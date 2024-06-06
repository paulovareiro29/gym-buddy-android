package ipvc.gymbuddy.api.models

data class Metric (
    val id: String,
    val type: MetricType?,
    val creator: SimplifiedUser,
    val value: Double,
    val date: String,
)