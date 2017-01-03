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

  /**
    * adds a new player to the global list of players.
    */
  post("/player") {
    val t = PlayerData.add(parsedBody.extract[PlayerRequest].name)
    PlayerResponse(t)
  }

  /**
    * returns a list of all players.
    */
  get("/player") {
    PlayerData.all
  }

  /**
    * returns information to the requested player.
    */
  get("/player/:token") {
    PlayerData.get(params("token")) match {
      case Some(player) => Ok(player)
      case None         => NotFound(ErrorResponse("No such player found."))
    }
  }

  /**
    * returns a list of all cars in the current game.
    */
  get("/car") {
    CarData.all
  }

  /**
    * returns information to the requested car.
    */
  get("/car/:token") {
    CarData.get(params("token")) match {
      case Some(car) => Ok(car)
      case None         => NotFound(ErrorResponse("No such car found."))
    }
  }

  /**
    * moves a car to the given position if the move is valid.
    */
  post("/car/move/:token") {
    def moveCar(car: Car) = {
      val req = parsedBody.extract[MoveRequest]
      val res = Controller.move(car, (req.x, req.y))
      MoveResponse(x = res._1._1, y = res._1._2, message = res._2)
    }

    CarData.get(params("token")) match {
      case Some(car) => Ok(moveCar(car))
      case None      => NotFound(ErrorResponse("The token has not been found in the car list. Probably the game has not started yet."))
    }
  }

  /**
    * starts a game with a selection of players and a track.
    * todo: assigns random start positions to the cars (on the start line)
    */
  post("/start") {
    val req = parsedBody.extract[StartRequest]
    StartResponse(Controller.startGame(req.player, req.track))
  }


  /**
    * ends a game and deletes all cars.
    */
  get("/end") {
    Controller.endGame
  }


  /**
    * returns a list of all tracks.
    */
  get("/track") {
    TrackData.all
  }

  /**
    * returns information to the requested track.
    */
  get("/track/:id") {
    TrackData.get(params("id").toInt) match {
      case Some(track) => Ok(track)
      case None         => NotFound(ErrorResponse("No such track found."))
    }
  }

  /**
    * adds a new track to the list of stored tracks.
    */
  post("/track") {
    val req = parsedBody.extract[TrackRequest]
    val wall = req.wall map (w => (w.x, w.y))
    val start = req.start map (s => (s.x, s.y))
    val finish = req.finish map (f => (f.x, f.y))
    TrackResponse(TrackData.add(wall, start, finish))
  }

  /**
    * registers a listener in order to get notified if another player changes the state of the game.
    */
  post("/listener") {

  }
}

case class StartRequest(player: List[String], track: Int)

case class StartResponse(message: String)

case class Pos(x: Int, y: Int)

case class TrackRequest(wall: List[Pos], start: List[Pos], finish: List[Pos])

case class TrackResponse(id: Int)

case class PlayerRequest(name: String)

case class PlayerResponse(token: String)

case class MoveRequest(x: Int, y: Int)

case class MoveResponse(x: Int, y: Int, message: String)

case class ErrorResponse(message: String)
