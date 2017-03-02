package model

/**
  * Created by patrick on 2/15/17.
  */
class UpdateMessage {

  var info: Map[String, Any] = Map()

  def add(key: String, value: Any) = {
    info += (key -> value)
  }

  override def toString: String = {
    val kvList = info map {
      case (k, v: Int) =>  "'" + k + "': " + v
      case (k, v: (Int, Int)) => "'" + k + "': { 'x': " + v._1 + ", 'y': " + v._2 + "}"
      case (k, v) =>  "'" + k + "': '" + v + "'"
    }
    "{" + kvList.mkString(",") + "}"
  }
}
