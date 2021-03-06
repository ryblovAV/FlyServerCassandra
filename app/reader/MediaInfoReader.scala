package reader

import utl.cassandra.FlyDatabase
import utl.{JsonUtl, MediaInfoKey}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Logger._

object MediaInfoReader {

  def find(keys: List[MediaInfoKey]) = {
    Future.sequence(
      keys.map(k =>
        FlyDatabase.flyFiles.getByKey(k))
    ).map(_.flatten)
      .map(l => JsonUtl.toJson(l))
  }

  def findDebug(keys: List[MediaInfoKey]) = {
    info(s"findDebug keys=$keys")
    keys.map(k => FlyDatabase.flyFiles.getByKeyDebug(k))
  }

}
