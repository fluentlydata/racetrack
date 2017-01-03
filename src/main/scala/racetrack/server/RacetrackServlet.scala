package racetrack

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._
import racetrack.server.Controller
import racetrack.server.model._

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

  get("/player/:token") {
    PlayerData.get(params("token")) match {
      case Some(player) => Ok(player)
      case None         => NotFound(ErrorResponse("No such player found."))
    }
  }

  get("/car") {
    CarData.all
  }

  get("/car/:token") {
    CarData.get(params("token")) match {
      case Some(car) => Ok(car)
      case None         => NotFound(ErrorResponse("No such car found."))
    }
  }

  post("/car/move/:token") {
    def moveCar(car: Car) = {
      val req = parsedBody.extract[MoveRequest]
      val res = Controller.move(car, (req.x, req.y))
      MoveResponse(x = res._1._1, y = res._1._2, status = res._2)
    }

    CarData.get(params("token")) match {
      case Some(car) => Ok(moveCar(car))
      case None      => NotFound(ErrorResponse("The token has not been found in the car list. Probably the game has not started yet."))
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

case class MoveRequest(x: Int, y: Int)

case class MoveResponse(x: Int, y: Int, status: String)

case class ErrorResponse(message: String)
