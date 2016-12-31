package racetrack

import model._

object Controller {

	type Vec = (Int, Int)

	// moves a car, returns new pos and whether an error occured
	def move(car: Car, pos: Vec): (Vec, Boolean) = {
		var c = add(car.pos, car.vec)
		var validFields = for {
			x <- -1 to 1
			y <- -1 to 1
		} yield add(c, (x,y))
		
		if (validFields contains pos) (pos, false) else (car.pos, true)
	}

	def add(a: Vec, b: Vec): Vec = (a._1+b._1,a._2+b._2)

	def startGame = {
		for (p <- PlayerData.all) CarData.add(p.token, (0,0)) 
	}

	def endGame = {
		CarData.clear
	}
}

