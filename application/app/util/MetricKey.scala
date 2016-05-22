package util

import play.api.libs.json.Writes

abstract class MetricKey[A : Writes](val name: String, val value: A) {
  def writes: Writes[A] = implicitly[Writes[A]]
}

object MetricKey {
  case class RequestId(override val value: String) extends MetricKey[String]("request_id", value)

  case class ResponseTime(override val  value: Long) extends MetricKey[Long]("response_time", value)

  case class StatusCode(override val  value: Int) extends MetricKey[Int]("status_code", value)

  case class RequestBody(override val  value: String) extends MetricKey[String]("request_body", value)

  case class Client(override val  value: String) extends MetricKey[String]("client", value)

  case class TransportationMode(override val  value: String) extends MetricKey[String]("transportation_mode", value)

  case class TravelTime(override val  value: Int) extends MetricKey[Int]("transportation_mode", value)

  case class RequestPath(override val  value: String) extends MetricKey[String]("request_path", value)

  case class FinalMessage(override val  value: Boolean) extends MetricKey[Boolean]("final_message", value)
}