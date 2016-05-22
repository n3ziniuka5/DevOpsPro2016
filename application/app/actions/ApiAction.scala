package actions

import java.util.UUID

import play.api.mvc.{ActionBuilder, Request, Result, WrappedRequest}
import util.LoggerExtensions._
import util.MetricKey
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class ApiRequest[A](val requestId: String, request: Request[A]) extends WrappedRequest[A](request)

object ApiAction extends ActionBuilder[ApiRequest] {
  def invokeBlock[A](request: Request[A], block: (ApiRequest[A]) => Future[Result]) = {
    println(request.toString())
    println(request.headers)

    val startTime = System.currentTimeMillis

    val requestId = UUID.randomUUID().toString
    val apiRequest = new ApiRequest(requestId, request)

    val result = block(apiRequest)
    result.onComplete { r =>
      val endTime = System.currentTimeMillis()
      metricsLogger.logMetrics(requestId, Set(
        MetricKey.ResponseTime(endTime - startTime),
        MetricKey.StatusCode(r.get.header.status),
        MetricKey.RequestBody(request.body.toString),
        MetricKey.RequestPath(request.path)
      ), finalMessage = true)
    }

    result
  }
}