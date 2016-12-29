package model

case class Player(name: String, token: String)

object PlayerData {
	var all: List[Player] = List()
	
	def add(name: String) = {
		all = Player(name, TokenGenerator.randomAlphanumericString(12)) :: all	
	}
}


// from: http://www.bindschaedler.com/2012/04/07/elegant-random-string-generation-in-scala/
object TokenGenerator {
	val random = new scala.util.Random

	def randomString(alphabet: String)(n: Int): String = {
		Stream.continually(random.nextInt(alphabet.size)).map(alphabet).take(n).mkString
	}

	def randomAlphanumericString(n: Int) = {
		randomString("abcdefghijklmnopqrstuvwxyz0123456789")(n)
	}
}
