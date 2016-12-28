package ch.buchter.app
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

import data.PlayerData

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
}

case class PlayerRequest(name: String)
