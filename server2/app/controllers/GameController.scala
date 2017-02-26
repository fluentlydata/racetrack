package controllers

import com.google.inject.Inject
import model.{Car, CarData, PlayerData}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

case class CarResponse(name: String, token: String, px: Int, py: Int, vx: Int, vy: Int)
object CarResponse {
  implicit val carFormat = Json.format[CarResponse]
}

class GameController @Inject() extends Controller {

  // GET /car
  def getAllCars = Action {
    val cars = CarData.all map (car => CarResponse(car.playerName, car.token, car.pos._1, car.pos._2, car.vel._1, car.vel._2))
    Ok(Json.toJson(cars))
  }

  // GET /car/:token
  def getOneCar(token: String) = Action {
    CarData.get(token) match {
      case Some(car) => Ok(Json.toJson(CarResponse(car.playerName, car.token, car.pos._1, car.pos._2, car.vel._1, car.vel._2)))
      case None => NotFound("No such car found.")
    }
  }


  /*
  post("/car/move/:token") {
    def moveCar(car: Car) = {
      val req = parsedBody.extract[MoveRequest]
      val res = Controller.move(car, (req.x, req.y))
      MoveResponse(x = res._1._1, y = res._1._2, message = res._2)
    }

    println("DEBUG (route '/move/:token'): " + params("token"))

    CarData.get(params("token")) match {
      case Some(car) => Ok(moveCar(car))
      case None => NotFound(ErrorResponse("The token has not been found in the car list. Probably the game has not started yet."))
    }
  }
  */

  /**
    * starts a game with a selection of players and a track.
    * todo: assigns random start positions to the cars (on the start line)
    */
  def start = Action {
    // val req = parsedBody.extract[StartRequest]
    // println("DEBUG (route '/start'): " + req.player)
    // val info = startGame(req.player, req.track)

    for (p <- PlayerData.all) CarData.add(p.name, p.token, (0, 0))
    Ok(views.html.game())
  }

}
