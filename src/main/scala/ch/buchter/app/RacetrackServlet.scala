package racetrack
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

import model._

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
		// todo: token of players should not be sent
	}

	post("/move") {
		var req = parsedBody.extract[MoveRequest]
		var player = PlayerData.get(req.token)
		if (player.nonEmpty) {
			var car = CarData.get(req.token) get // todo no proper error handling
			var res: ((Int, Int), Boolean) = Controller.move(car, (req.x, req.y))
			MoveResponse(res._1._1, res._1._2, res._2)
		} else {
			MoveResponse(0,0, false)
			// todo: 404 not found?
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
case class MoveResponse(x: Int, y: Int, status: Boolean)
