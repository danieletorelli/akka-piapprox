package it.mdtorelli.piapprox.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
  * Author: Daniele Torelli
  * Project: akka-piapprox
  * Date: 29/11/15
  * Time: 20:42
  */
class WorkerSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("WorkerSpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
  "A worker" must {
    import Worker._

    "calculate pi approximation" in {
      val worker = system.actorOf(Props[Worker])
      worker ! Work(0, 1)
      expectMsg(Result(4.0))
    }

  }

}
