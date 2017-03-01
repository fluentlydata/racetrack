package model

import play.api.libs.json.Json

case class Field(x: Int, y: Int, t: Int)
object Field {
  implicit val fieldFormat = Json.format[Field]
}

case class Track(id: Int, fields: List[Field])
object Track {
  implicit val trackFormat = Json.format[Track]
}

object TrackData {
  // todo: remove default track later
  var all: List[Track] = List(DefaultTrack.track)

  def add(fields: List[Field]) = {
    val id = all.size
    all = Track(id, fields) :: all
    id
  }

  def get(id: Int): Option[Track] = {
    // passes back the first
    all find (_.id == id)
  }
}

object DefaultTrack {
  val track: Track = Track(0, List(
    Field(0,1,0),
    Field(0,2,0),
    Field(0,3,0),
    Field(0,4,0),
    Field(0,5,0),

    Field(2,1,0),
    Field(2,2,0),
    Field(2,3,0),
    Field(2,4,0),
    Field(2,5,0),

    Field(0,6,1),
    Field(1,6,1),
    Field(2,6,1),

    Field(0,0,2),
    Field(1,0,2),
    Field(2,0,2)
  ))
}
