package racetrack.server

import scala.collection.mutable.Queue

/**
  * Created by patrick on 2/11/17.
  * parallelism is only on getting data, not on modifying data, so no lock mechanism is needed.
  */
class GameState {

  var running = false
  var updates: Map[String, Queue[String]] = Map()

  def isRunning: Boolean = {
    true
  }

  def hasUpdate(token: String): Boolean = {
    if (updates.contains(token)) {
      updates(token).nonEmpty
    } else {
      false
    }
  }

  def getNextUpdate(token: String): String = {
    updates(token).dequeue()
  }

  def addUpdate(update: String, token: String) = {
    updates(token).enqueue(update)
  }

  def addUpdateForAll(update: String): Unit = {
    for ((_, queue) <- updates) queue.enqueue(update)
  }

  def addUpdateForAllExcept(update: String, token: String) = {
    for ((_, queue) <- updates filter (x => x._1 != update)) queue.enqueue(update)
  }
}
