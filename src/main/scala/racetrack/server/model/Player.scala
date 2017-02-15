package racetrack.server.model

case class Player(name: String, token: String)

object PlayerData {
  var all: List[Player] = List()

  def add(name: String): String = {
    var t = TokenGenerator.randomAlphanumericString(12)
    all = Player(name, t) :: all
    t
  }

  def get(t: String): Option[Player] = {
    // passes back the first
    println("DEBUG: PlayerData.get(" + t + ")")
    all find (_.token == t)
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
