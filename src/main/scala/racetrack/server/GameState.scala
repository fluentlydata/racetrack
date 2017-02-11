package racetrack.server

import scala.collection.mutable.Queue

/**
  * Created by patrick on 2/11/17.
  * parallelism is only on getting data, not on modifying data, so no lock mechanism is needed.
  */
class GameState {
  var running = false

  // we maintain a history of all updates. if a new listener registers, he can enqueue all events and recreate the game state.
  var history: List[String] = List()

  // key: id of update handler
  var updates: Map[Int, Queue[String]] = Map()


  def isRunning: Boolean = {
    true
  }

  def hasUpdate(handlerId: Int): Boolean = {
    // add new queue for every unknown handlerId
    if (!updates.contains(handlerId)) {
      updates += (handlerId -> Queue())
      for (h <- history) updates(handlerId).enqueue(h)
    }
    updates(handlerId).nonEmpty
  }

  def getNextUpdate(handlerId: Int): String = {
    updates(handlerId).dequeue()
  }

  def addUpdate(update: String) = {
    history = update :: history
    for ((_,queue) <- updates) queue.enqueue(update)
  }
}
