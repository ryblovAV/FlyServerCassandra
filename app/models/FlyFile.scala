package models

case class Media(flyAudio: Option[String],
                 flyAudioBr: Option[String],
                 flyVideo: Option[String],
                 flyXY: Option[String])

case class FlyFile(tth: String,
                   size: String,
                   media: Media,
                   firstDate: Int,
                   countPlus: Option[Int],
                   countMinus: Option[Int],
                   countTake: Option[Int],
                   countDownload: Option[Int],
                   countUpload: Option[Int],
                   countQuery: Int,
                   countMedia: Option[Int],
                   countAntivirus: Option[Int])
