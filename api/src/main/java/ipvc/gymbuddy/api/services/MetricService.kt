package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IMetricsService
import ipvc.gymbuddy.api.models.responses.metric.GetAllMetricsResponse
import ipvc.gymbuddy.api.models.responses.metric.GetMetricResponse

class MetricService : HttpClient<IMetricsService>(IMetricsService::class.java) {

    suspend fun getMetrics(userId : String): RequestResult<GetAllMetricsResponse> {
        return when (val response = request(api.getMetrics(userId))) {
            is RequestResult.Success -> {
                val parsedData = ResponseParser.payload<GetAllMetricsResponse>(response)
                RequestResult.Success(response.code, response.message, parsedData)
            }
            is RequestResult.Error -> response
        }
    }

    suspend fun getMetric(id: String): RequestResult<GetMetricResponse> {
        return when (val response = request(api.getMetric(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetMetricResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
