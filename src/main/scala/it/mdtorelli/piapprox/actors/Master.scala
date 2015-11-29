package it.mdtorelli.piapprox.actors

import akka.actor.{ActorRef, Props, Actor}
import akka.routing.RoundRobinPool

/**
  * Author: Daniele Torelli
  * Project: akka-piapprox
  * Date: 29/11/15
  * Time: 20:51
  */
object Master {
  case object Calculate
}

class Master(workers: Int, messages: Int, elements: Int, listener: ActorRef) extends Actor {
  import Master._

  var pi: Double = _
  var results: Int = _
  val start = System.currentTimeMillis

  // Initialize workers pool
  val workersPool = context.actorOf(RoundRobinPool(workers).props(Props[Worker]),
                                    name = "workersPool")

  override def receive = {
    case Calculate =>
      // Split calculation for every worker
      for (i <- 0 until messages) {
        workersPool ! Worker.Work(i * elements, elements)
      }

    case Worker.Result(value) =>
      // Approximate π with new computation result
      pi += value
      results += 1
      if (results == messages) {
        // Tell final π approximation to the listener and die
        listener ! Listener.PiApproximation(pi, start, System.currentTimeMillis )
        context.stop(self)
      }
  }

}
