package it.mdtorelli.piapprox.actors

import akka.actor.Actor

/**
  * Author: Daniele Torelli
  * Project: akka-piapprox
  * Date: 29/11/15
  * Time: 20:34
  */
object Worker {
  case class Work(start: Int, elements: Int)
  case class Result(value: Double)
}

class Worker extends Actor {
  import Worker._

  override def receive = {
    case Work(start, elements) =>
      sender ! Result(calculatePi(start, elements))
  }

  // Use the Leibniz formula for π
  private def calculatePi(start: Int, elements: Int): Double = {
    var acc = 0.0
    for (i <- start until (start + elements)) {
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    }
    acc
  }

}
