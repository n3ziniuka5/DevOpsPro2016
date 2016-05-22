package util

import play.api.Logger
import play.api.libs.json.{Json, Writes}

object LoggerExtensions {
  val metricsLogger = Logger("metrics")

  implicit class LoggerExtensions(val logger: Logger) extends AnyVal {
    def logMetrics(requestId: String, metrics: Set[MetricKey[_]], finalMessage: Boolean = false): Unit = log(
      metrics +
      MetricKey.RequestId(requestId) +
      MetricKey.FinalMessage(finalMessage)
    )

    private def log(metrics: Set[MetricKey[_]]): Unit = {
      implicit val metricWrites = new Writes[MetricKey[Any]] {
        def writes(m: MetricKey[Any]) = Json.toJson(m.value)(m.writes)
      }

      val metricMap: Map[String, MetricKey[Any]] = metrics.map(m => (m.name, m.asInstanceOf[MetricKey[Any]])).toMap
      val jsonString = Json.toJson(metricMap)

      metricsLogger.info(jsonString.toString())
    }
  }
}