package ch.buchter.app
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

import model._

class RacetrackServlet extends ScalatraServlet with JacksonJsonSupport {

	protected implicit lazy val jsonFormats: Formats = DefaultFormats.withBigDecimal

	before() {
		contentType = formats("json")
	}

	post("/player") {
		PlayerData.add(parsedBody.extract[PlayerRequest].name)
	}

	get("/player") {
		PlayerData.all
	}

	get("/start") {
		// call controller -> add a par per player
		// and start the game
	}
}

case class PlayerRequest(name: String)
