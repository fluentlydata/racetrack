package model

import org.slf4j.LoggerFactory

// case class Car(token: String, posx: Int, posy: Int, velx: Int, vely: Int)
case class Car(playerName: String, token: String, pos: (Int, Int), vel: (Int, Int))

object CarData {
  var all: List[Car] = List()

  val logger = LoggerFactory.getLogger(getClass)

  def add(playerName: String, token: String, pos: (Int, Int)) = {
    println("adding car: " + token)
    all = Car(playerName, token, pos, (0, 0)) :: all
  }

  def update(playerName: String, token: String, pos: (Int, Int), vec: (Int, Int)) = {
    val idx = (for {c <- all} yield c.token) indexOf token
    logger.debug("Car.update " + idx.toString)
    all = all.patch(idx, Seq(Car(playerName, token, pos, vec)), 1)
  }

  def get(t: String): Option[Car] = {
    // passes back the first
    all find (_.token == t)
  }

  def clear = {
    all = List()
  }
}
