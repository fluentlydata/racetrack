package controllers

import com.google.inject.Inject
import model.Player
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by patrick on 2/25/17.
  */
class PlayerController @Inject() extends Controller {

  var players: List[Player] = List()

  // POST /player
  def addPlayer = Action { implicit request =>
    val player = playerForm.bindFromRequest.get
    players = player :: players
    // Redirect(routes.HomeController.index())
    Ok
  }

  val playerForm: Form[Player] = Form {
    mapping(
    "name" -> text
    )(Player.apply)(Player.unapply)
  }


  // GET /player
  def getPlayer = Action {
    Ok(Json.toJson(players))
  }
}
