package model

import play.api.libs.json.Json

case class Player(name: String)

object Player {
  implicit val playerFormat = Json.format[Player]
}
