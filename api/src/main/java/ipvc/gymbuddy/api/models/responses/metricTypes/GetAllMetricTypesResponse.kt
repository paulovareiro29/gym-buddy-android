package ipvc.gymbuddy.api.models.responses.metricTypes

import ipvc.gymbuddy.api.models.MetricType

data class GetAllMetricTypesResponse (
    val metricTypes: List<MetricType>
)