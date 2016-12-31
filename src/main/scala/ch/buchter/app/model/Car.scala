package model

case class Car(token: String, pos: (Int, Int), vec: (Int, Int))

object CarData {
	var all: List[Car] = List()
	
	def add(token: String, pos: (Int, Int)) = {
		all = Car(token, pos, (0,0)) :: all	
	}

	def get(t: String): Option[Car] = {
        // passes back the first
        all find (_.token == t)
    }

	def clear = {
		all = List()
	}
}


