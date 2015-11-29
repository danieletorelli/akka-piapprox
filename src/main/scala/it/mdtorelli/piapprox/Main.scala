package it.mdtorelli.piapprox

import akka.actor.{Props, ActorSystem}
import it.mdtorelli.piapprox.actors.{Master, Listener}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val workers = 4
  val messages = 10000
  val elements = 10000

  // Initialize actor system
  val system = ActorSystem("PiApproxSystem")

  // Initialize listener and master actors
  val listener = system.actorOf(Props[Listener], name = "listener")
  val master = system.actorOf(Props(new Master(workers, messages, elements, listener)),
                              name = "master")

  // Tell master to start calculation
  master ! Master.Calculate

  Await.result(system.whenTerminated, Duration.Inf)
}