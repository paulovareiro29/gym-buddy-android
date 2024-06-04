package ipvc.gymbuddy.api.models

data class Metric (
    val id: String,
    val metricType: MetricType?,
    val creator: SimplifiedUser,
    val value: Double,
)