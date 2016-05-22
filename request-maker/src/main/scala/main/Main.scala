package main

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, _}
import scala.util.Random

object Main {
  val clients = Vector("Hightexon", "Tamtrans", "Zootechnology", "Quolux", "Matcorporation", "Hatplus", "Street-nix", "Rankex")
  val transportationModes = Vector("public_transport", "driving", "walking", "cycling")
  val travelTimes = Vector(900, 1800, 3600, 7200)

  case class Request(client: String, transportationMode: String, travelTime: Int)

  implicit val requestWrites = Json.writes[Request]

  def randomFromCollection[A](col: Vector[A]): A = col(Random.nextInt(col.size))

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    val ws = AhcWSClient()

    system.scheduler.schedule(0.seconds, 10.seconds) {
      for (i <- 1 to Random.nextInt(100)) {
        println("sending request")
        val request = randomRequest()
        val response = ws.url("http://localhost:9000/").post(Json.toJson(request))
        Await.result(response, Duration.Inf)
      }
    }
  }

  def randomRequest(): Request = {
    val client = randomFromCollection(clients)
    val travelTime = randomFromCollection(travelTimes)
    val transporationMode = if (client == "Hatplus" && Random.nextInt(10) == 0) {
      "cyclingg"
    } else {
      randomFromCollection(transportationModes)
    }

    Request(client, transporationMode, travelTime)
  }


}
