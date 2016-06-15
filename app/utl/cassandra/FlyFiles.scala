package utl.cassandra

import com.websudos.phantom.dsl._
import models.{FlyFile, Media}
import utl.MediaInfoKey

import scala.concurrent.Future


abstract class FlyFiles extends CassandraTable[ConcreteFlyFiles, FlyFile] {

  object tth extends StringColumn(this) with PartitionKey[String]

  object size extends StringColumn(this) with PartitionKey[String]

  object flyAudio extends OptionalStringColumn(this)

  object flyAudioBr extends OptionalStringColumn(this)

  object flyVideo extends OptionalStringColumn(this)

  object flyXY extends OptionalStringColumn(this)

  object firstDate extends IntColumn(this)

  object countPlus extends OptionalIntColumn(this)

  object countMinus extends OptionalIntColumn(this)

  object countTake extends OptionalIntColumn(this)

  object countDownload extends OptionalIntColumn(this)

  object countUpload extends OptionalIntColumn(this)

  object countQuery extends IntColumn(this)

  object countMedia extends OptionalIntColumn(this)

  object countAntivirus extends OptionalIntColumn(this)

  def fromRow(row: Row): FlyFile = {
    FlyFile(
      tth = tth(row),
      size = size(row),
      media = Media(
        flyAudio = flyAudio(row),
        flyAudioBr = flyAudioBr(row),
        flyVideo = flyVideo(row),
        flyXY = flyXY(row)
      ),
      firstDate = firstDate(row),
      countPlus = countPlus(row),
      countMinus = countMinus(row),
      countTake = countTake(row),
      countDownload = countDownload(row),
      countUpload = countUpload(row),
      countQuery = countQuery(row),
      countMedia = countMedia(row),
      countAntivirus = countAntivirus(row)
    )
  }
}

abstract class ConcreteFlyFiles extends FlyFiles with Connector {

  def store(flyFile: FlyFile): Future[ResultSet] = {
    insert.value(_.tth, flyFile.tth)
      .value(_.size, flyFile.size)
      .value(_.flyAudio, flyFile.media.flyAudio)
      .value(_.flyAudioBr, flyFile.media.flyAudioBr)
      .value(_.flyVideo, flyFile.media.flyVideo)
      .value(_.flyXY, flyFile.media.flyXY)
      .value(_.firstDate, flyFile.firstDate)
      .value(_.countPlus, flyFile.countPlus)
      .value(_.countMinus, flyFile.countMinus)
      .value(_.countTake, flyFile.countTake)
      .value(_.countDownload, flyFile.countDownload)
      .value(_.countUpload, flyFile.countUpload)
      .value(_.countQuery, flyFile.countQuery)
      .value(_.countMedia, flyFile.countMedia)
      .value(_.countAntivirus, flyFile.countAntivirus)
      .future()
  }

  def getByKey(mediaInfoKey: MediaInfoKey): Future[Option[FlyFile]] = {
    select.where(_.tth eqs mediaInfoKey.tth).and(_.size eqs mediaInfoKey.size).consistencyLevel_=(ConsistencyLevel.ONE).one()
  }

}