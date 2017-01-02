package racetrack

import model._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

// todo: general, proper error handling
class RacetrackServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    contentType = formats("json")
  }

  post("/player") {
    var t = PlayerData.add(parsedBody.extract[PlayerRequest].name)
    PlayerPostResponse(t)
  }

  get("/player") {
    PlayerData.all
  }

  get("/car") {
    CarData.all
  }

  post("/move") {
    val req = parsedBody.extract[MoveRequest]
    val player = PlayerData.get(req.token)
    if (player.nonEmpty) {
      val car = CarData.get(req.token) get // todo no proper error handling
      val res = Controller.move(car, (req.x, req.y))
      MoveResponse(res)
    } else {
      MoveResponse(false)
      // todo: 404
    }
  }

  get("/start") {
    Controller.startGame
  }

  get("/end") {
    Controller.endGame
  }

  post("/track") {
    // posts a new track to the list of all tracks
  }

  get("/track") {

  }

  post("/track/:id") {

  }
}

case class PlayerRequest(name: String)

case class PlayerPostResponse(token: String)

case class MoveRequest(token: String, x: Int, y: Int)

case class MoveResponse(status: Boolean)
