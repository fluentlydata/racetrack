import sbt._
import Keys._
import org.scalatra.sbt._

object ScalatraSanbox extends Build {
  val Organization = "Buchter AG"
  val Name = "Racetrack"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.8"
  val json4sversion = "3.3.0.RC1"
  val ScalatraVersion = "2.4.0-RC2-2"
  val jettyVersion = "9.1.3.v20140225"

  lazy val project = Project (
    "scalatra-websockets-sandbox",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra"                %% "scalatra" % ScalatraVersion,
        "org.scalatra"                %% "scalatra-atmosphere"   % ScalatraVersion,
        "org.json4s"                  %% "json4s-jackson"        % json4sversion,

        "ch.qos.logback"               % "logback-classic"       % "1.1.2" % "runtime",

        "org.eclipse.jetty"            %  "jetty-plus"           % jettyVersion % "container",
        "org.eclipse.jetty.websocket"  %  "websocket-server"     % jettyVersion % "container",
        "org.eclipse.jetty"            %  "jetty-webapp"         % jettyVersion % "container",
        "javax.servlet"                %  "javax.servlet-api"    % "3.1.0" % "provided"
      )
    )
  )
}
