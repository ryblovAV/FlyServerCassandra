package controllers

import play.api.mvc._
import reader.MediaInfoReader
import utl.{JsonUtl, MediaInfoKey, ZLibUtl}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Logger._

class Application extends Controller {

  def getInfo = Action.async(parse.raw) {
    request =>
      request.body.asBytes() match {
        case Some(bs) =>
          val requestInfo = JsonUtl.parse(ZLibUtl.decompress(bs.toArray))

          debug(s"requestInfo.array = ${requestInfo.array}")

          MediaInfoReader.find(requestInfo.array)
            .map(js => ZLibUtl.compress(js.toString.getBytes))
            .map(mediaInfoCompress => Ok(mediaInfoCompress))

        case None => Future {
          BadRequest
        }
      }
  }

  def getInfoTest(tth: String, size: String) = Action {
    MediaInfoReader.findDebug(List(MediaInfoKey(tth = tth, size = size)))
    Ok
  }


}