package racetrack.server.model

import org.slf4j.LoggerFactory

case class Track(id: Int, wall: List[(Int, Int)], start: List[(Int, Int)], finish: List[(Int, Int)])

object TrackData {
  var all: List[Track] = List()

  val logger = LoggerFactory.getLogger(getClass)

  def add(wall: List[(Int, Int)], start: List[(Int, Int)], finish: List[(Int, Int)]) = {
    val id = all.size
    all = Track(id, wall, start, finish) :: all
    logger.debug("wall of track " + id + ": " + all(id).wall)
    id
  }

  def get(id: Int): Option[Track] = {
    // passes back the first
    all find (_.id == id)
  }
}
