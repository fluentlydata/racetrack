package racetrack

import model._

object Controller {

	type Vec = (Int, Int)

	// moves a car, returns new pos and whether an error occured
	def move(car: Car, pos: Vec): Boolean = {
		val c = add(car.pos, car.vec)
		val validFields = for {
			x <- -1 to 1
			y <- -1 to 1
		} yield add(c, (x,y))

    CarData.logger.info("car.pos: " + car.pos + " || new pos: " + pos + " || car.vec: " + car.vec)

		if (validFields contains pos) {
			CarData.update(car.token, pos, sub(pos, car.pos))
			true
		} else {
			false
		}
	}

	def add(a: Vec, b: Vec): Vec = (a._1+b._1,a._2+b._2)
	def sub(a: Vec, b: Vec): Vec = (a._1-b._1,a._2-b._2)

	def startGame = {
		for (p <- PlayerData.all) CarData.add(p.token, (0,0)) 
	}

	def endGame = {
		CarData.clear
	}
}

