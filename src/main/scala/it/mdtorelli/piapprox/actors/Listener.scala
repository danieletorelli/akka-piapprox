package it.mdtorelli.piapprox.actors

import akka.actor.Actor

import scala.concurrent.duration.Duration

/**
  * Author: Daniele Torelli
  * Project: akka-piapprox
  * Date: 29/11/15
  * Time: 21:02
  */
object Listener {
  case class PiApproximation(pi: Double, start: Long, end: Long)
}

class Listener extends Actor {
  import Listener._

  override def receive = {
    case PiApproximation(pi, start, end) =>
      // Print received final π approximation and terminate the actor system
      println(s"Pi approximation: $pi")
      println(s"Calculation time: ${Duration(end - start, "millis")}")
      context.system.terminate()
  }

}
