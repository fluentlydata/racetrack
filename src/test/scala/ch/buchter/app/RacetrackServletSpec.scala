package ch.buchter.app

import org.scalatra.test.specs2._
import racetrack.RacetrackServlet

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class RacetrackServletSpec extends ScalatraSpec {
  def is =
    "GET / on RacetrackServlet" ^
      "should return status 200" ! root200 ^
      end

  addServlet(classOf[RacetrackServlet], "/*")

  def root200 = get("/") {
    status must_== 200
  }
}
