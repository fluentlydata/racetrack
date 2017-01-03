package racetrack.server

import racetrack.server.model._

object Controller {

	var activeTrack: Track = _

	type Vec = (Int, Int)

	// moves a car, returns new pos and whether an error occurred
	def move(car: Car, pos: Vec): (Vec, String) = {
		val c = add(car.pos, car.vec)
		val validFields = for {
			x <- -1 to 1
			y <- -1 to 1
		} yield add(c, (x,y))

    CarData.logger.info("car.pos: " + car.pos + " || new pos: " + pos + " || car.vec: " + car.vec)

		if (validFields contains pos) {
			CarData.update(car.token, pos, sub(pos, car.pos))
			(pos, "Moved car to new position.")
		} else {
			(car.pos, "Invalid move request.")
		}
	}

	def add(a: Vec, b: Vec): Vec = (a._1+b._1,a._2+b._2)
	def sub(a: Vec, b: Vec): Vec = (a._1-b._1,a._2-b._2)

	def startGame(player: List[String], track: Int) = {
    TrackData.get(track) match {
      case Some(t) => {
        activeTrack = t
        for (p <- player) CarData.add(p, (0,0))
        "Game started."
      }
      case None    => "Couldn't start game. No track ID " + track.toString + " found."
    }
	}

	def endGame = {
		CarData.clear
    activeTrack = null
	}
}

