package model

case class Car(playerName: String, pos: (Int, Int), vec: (Int, Int))

object CarData {
	var all: List[Car] = List()
	
	def add(name: String, pos: (Int, Int)) = {
		all = Car(name, pos, (0,0)) :: all	
	}
}


