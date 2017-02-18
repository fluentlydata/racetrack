import java.net._
import java.io._
import scala.io._

val s = new Socket(InetAddress.getByName("localhost"), 9999)
lazy val in = new BufferedSource(s.getInputStream())

while(true) {
  Thread.sleep(100)
  println("From server: " + in.getLines().next())
}


