package racetrack.server

import java.net.{Socket, ServerSocket}
import java.io._

class NetworkService(port: Int, game: GameState) extends Runnable {
  val serverSocket = new ServerSocket(port)
  var numHandler = 0
  def run() {
    while (true) {
      println("Listening... number of current listeners: " + numHandler)
      val socket = serverSocket.accept() // This will block until a connection comes in.
      new Thread(new Handler(socket, numHandler, game)).start()
      numHandler = numHandler + 1
    }
  }
}

class Handler(socket: Socket, handlerId: Int, game: GameState) extends Runnable {
  val threadName = Thread.currentThread.getName
  def run() {
    val out = new PrintStream(socket.getOutputStream)

    out.println("You're connected to " + threadName + " with id: " + handlerId)
    out.flush()

    while (game.isRunning) {
      Thread.sleep(500)
      if (game.hasUpdate(handlerId)) {
        val updateMessage = game.getNextUpdate(handlerId)
        out.println("Having new update from " + threadName + ": " + updateMessage)
        out.flush()
      }
    }
    socket.close()
  }
}

