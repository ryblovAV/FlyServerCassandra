package utl

import config.JsonFieldName._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, _}
import utl.cassandra.{FlyFile, Media}

case class MediaInfoKey(size: String, tth: String)

case class RequestData(array: List[MediaInfoKey])

object JsonUtl {

  implicit val MediaInfoKeyReads: Reads[MediaInfoKey] = (
    (__ \ 'size).read[String] and
      (__ \ 'tth).read[String]
    ) (MediaInfoKey.apply _)

  implicit val RequestDataReads: Reads[RequestData] =
    ((__ \ 'array).read[List[MediaInfoKey]] ) map (RequestData.apply _)

  implicit val mediaWriters = new Writes[Media] {
    override def writes(media: Media) = Json.obj(
      FLY_AUDIO -> media.flyAudio,
      FLY_AUDIO_BR -> media.flyAudioBr,
      FLY_VIDEO -> media.flyVideo,
      FLY_XY -> media.flyXY
    )
  }

  implicit val flyFileWriters = new Writes[FlyFile] {
    override def writes(flyFile: FlyFile) = Json.obj(
      MEDIA -> flyFile.media,
      SIZE -> flyFile.size,
      TTH -> flyFile.tth
    )
  }

  implicit val FlyArrayWriters = new Writes[List[FlyFile]] {
    override def writes(flyFiles: List[FlyFile]): JsValue = Json.obj(
      ARRAY -> flyFiles
    )
  }

  def parse(str: String): RequestData = {
    Json.parse(str).as[RequestData]
  }

  def toJson(flyFiles: List[FlyFile]) = {
    Json.toJson(flyFiles)
  }

}
