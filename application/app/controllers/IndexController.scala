package controllers

import javax.inject._

import actions.ApiAction
import dto.SampleRequest
import play.api._
import play.api.mvc._
import util.LoggerExtensions._
import util.MetricKey

@Singleton
class IndexController @Inject() extends Controller {
  val logger = Logger.logger

  def index = ApiAction(parse.json[SampleRequest]) { request =>
    val body = request.body

    metricsLogger.logMetrics(request.requestId, Set(
      MetricKey.Client(body.client),
      MetricKey.TransportationMode(body.transportationMode),
      MetricKey.TravelTime(body.travelTime)
    ))

    if(body.transportationMode == "cyclingg") {
      BadRequest
    } else {
      Ok
    }
  }
}
