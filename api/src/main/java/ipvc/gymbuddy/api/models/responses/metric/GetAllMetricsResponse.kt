package ipvc.gymbuddy.api.models.responses.metric

import ipvc.gymbuddy.api.models.Metric

data class GetAllMetricsResponse (
    val metrics: List<Metric>
)
