package racetrack.server

import java.util.concurrent.Executors

import org.json4s.JsonAST.{JNull, JField, JString, JObject}
import org.json4s.{JsonDSL, JValue, DefaultFormats, Formats}
import org.scalatra.SessionSupport
import org.scalatra._
import org.scalatra.json.{JacksonJsonSupport, JValueResult}
import org.slf4j.LoggerFactory
import org.scalatra.atmosphere._
import JsonDSL._
import scala.concurrent.{ExecutionContext, Future}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source
import scala.util.{Success, Failure}

// largely from: https://github.com/papito/scalatra-websockets-sandbox/blob/master/src/main/scala/com/whyisitdoingthat/controllers/WebsocketController.scala
class WebsocketController extends ScalatraServlet with JValueResult with JacksonJsonSupport with SessionSupport with AtmosphereSupport   {
  private final val log = LoggerFactory.getLogger(getClass)

  implicit protected val jsonFormats: Formats = DefaultFormats

  atmosphere("/") {
    new AtmosphereClient {
      private def uuidJson: JObject = "uid" -> uuid
      private var abortFutures = false
      private val rand = scala.util.Random

      override def receive: AtmoReceive = {

        case message => {
          println(message)
        }

          /*
        case message @ JsonMessage(AnyRef) => {
          val json: JValue = message.content
          log.info(s"WS <- $json")
          this.broadcast(json, Everyone)
        }
        */

        case Connected =>
          log.info("Client connected")

        case Disconnected(disconnector, Some(error)) =>
          log.info("Client disconnected ")

        case Error(Some(error)) =>
          // FIXME - what is the difference with the servlet-level "error" handler?
          error.printStackTrace()
      }
    }
  }
}
