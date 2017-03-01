package controllers

import com.google.inject.Inject
import model.{Player, PlayerData}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by patrick on 2/25/17.
  */
class PlayerController @Inject() extends Controller {

  // todo: now, the client needs to send a "token" attribute which is not used...
  val playerForm: Form[Player] = Form {
    mapping(
      "name" -> text,
      "token" -> text
    )(Player.apply)(Player.unapply)
  }

  // POST /player
  def addPlayer = Action { implicit request =>
    val player = playerForm.bindFromRequest.get
    PlayerData.add(player.name)
    // players = player :: players
    Redirect(routes.HomeController.index())
  }

  // GET /player
  def getAllPlayers = Action {
    Ok(Json.toJson(PlayerData.all))
  }

  def getPlayer(token:String) = Action {
    PlayerData.get(token) match {
      case Some(player) => Ok(Json.toJson(player))
      case None         => NotFound
    }
  }
}
