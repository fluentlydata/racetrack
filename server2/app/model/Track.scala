package model

case class Track(id: Int, wall: List[(Int, Int)], start: List[(Int, Int)], finish: List[(Int, Int)])

object TrackData {
  // todo: remove default track later
  var all: List[Track] = List(DefaultTrack.track)

  def add(wall: List[(Int, Int)], start: List[(Int, Int)], finish: List[(Int, Int)]) = {
    val id = all.size
    all = Track(id, wall, start, finish) :: all
    println("wall of track " + id + ": " + all(id).wall)
    id
  }

  def get(id: Int): Option[Track] = {
    // passes back the first
    all find (_.id == id)
  }
}

object DefaultTrack {
  val track: Track = Track(0, List(
    (0,1),
    (0,2),
    (0,3),
    (0,4),
    (0,5),
    (2,1),
    (2,2),
    (2,3),
    (2,4),
    (2,5)
  ), List(
    (0,6),
    (1,6),
    (2,6)
  ), List(
    (0,0),
    (1,0),
    (2,0)
  ))
}
