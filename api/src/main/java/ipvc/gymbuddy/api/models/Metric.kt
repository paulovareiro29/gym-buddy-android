package ipvc.gymbuddy.api.models

import java.util.Date

data class Metric (
    val id: String,
    val user: SimplifiedUser,
    val type: MetricType?,
    val creator: SimplifiedUser,
    val value: String,
    val date: Date
)