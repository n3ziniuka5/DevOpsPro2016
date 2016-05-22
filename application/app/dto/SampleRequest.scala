package dto

import play.api.libs.json.Json

case class SampleRequest(client: String, travelTime: Int, transportationMode: String)

object SampleRequest {
  implicit val reads = Json.reads[SampleRequest]
}
