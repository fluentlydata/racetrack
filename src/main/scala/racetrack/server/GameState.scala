package racetrack.server

import scala.collection.mutable.Queue

/**
  * Created by patrick on 2/11/17.
  * parallelism is only on getting data, not on modifying data, so no lock mechanism is needed.
  */
class GameState {

  var running = false
  val queue = new Queue[String]

  def isRunning: Boolean = {
    true
  }

  def hasUpdate(handlerId: Int): Boolean = {
    queue.nonEmpty
  }

  def getNextUpdate(handlerId: Int): String = {
    println("DEBUG: Update requested. Returning: " + queue.front)
    // todo: proper enqueing
    queue.front
  }

  def addUpdate(update: String) = {
    queue.enqueue(update)
    println("DEBUG: GameState: queue: " + queue)
  }
}
