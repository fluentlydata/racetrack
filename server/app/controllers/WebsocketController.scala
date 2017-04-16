package controllers

import com.google.inject.Inject
import model.GameState
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class WebsocketController @Inject()(ws: WSClient) extends Controller {

  var listenerCount = 0

  def index = Action {
    // need braces here, even if empty --> why?
    Ok(views.html.ws_test())
  }

  def registerListener = WebSocket.using[String] {
    request => {

      def getUpdateString: String = {
        if (GameState.hasUpdate(listenerCount)) GameState.getNextUpdate(listenerCount).toString
        else "[]"
      }

      def getTimeout = {
        if (GameState.hasUpdate(listenerCount)) 0 else 1000
      }

      // ignoring any inputs
      val in: Iteratee[String, Unit] = Iteratee.ignore[String]

      //
      val out: Enumerator[String] = Enumerator.repeatM(Promise.timeout(getUpdateString, getTimeout))

      // val out: Enumerator[String] = Enumerator.repeatM(Promise.timeout(s"${new java.util.Date()}", 1000))
      // val out = Enumerator("Hello!").andThen(Enumerator.eof)

      listenerCount = listenerCount + 1

      (in, out)
    }
  }

  // endpoint that opens an echo websocket
  def wsEcho = WebSocket.using[String] {
    request => {
      println(s"wsEcho, client connected.")
      var channel: Option[Concurrent.Channel[String]] = None
      val outEnumerator: Enumerator[String] = Concurrent.unicast(c => channel = Some(c))

      val inIteratee: Iteratee[String, Unit] = Iteratee.foreach[String](receivedString => {
        // send string back
        println(s"wsEcho, received: $receivedString")
        channel.foreach(_.push(receivedString))
      })

      (inIteratee, outEnumerator)
    }
  }

  // sends the time every second, ignores any input
  def wsTime = WebSocket.using[String] {
    request =>
      println(s"wsTime, client connected.")

      val outEnumerator: Enumerator[String] = Enumerator.repeatM(Promise.timeout(s"${new java.util.Date()}", 1000))
      val inIteratee: Iteratee[String, Unit] = Iteratee.ignore[String]

      (inIteratee, outEnumerator)
  }
}
