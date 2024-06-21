package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IMetricsService
import ipvc.gymbuddy.api.models.requests.metric.CreateMetricRequest
import ipvc.gymbuddy.api.models.requests.metric.UpdateMetricRequest
import ipvc.gymbuddy.api.models.responses.metric.CreateMetricResponse
import ipvc.gymbuddy.api.models.responses.metric.DeleteMetricResponse
import ipvc.gymbuddy.api.models.responses.metric.GetAllMetricsResponse
import ipvc.gymbuddy.api.models.responses.metric.GetMetricResponse
import ipvc.gymbuddy.api.models.responses.metric.UpdateMetricResponse

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

    suspend fun createMetric(body: CreateMetricRequest): RequestResult<CreateMetricResponse> {
        return when(val response = request(api.createMetric(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateMetricResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun updateMetric(id: String, body: UpdateMetricRequest): RequestResult<UpdateMetricResponse> {
        return when(val response = request(api.updateMetric(body, id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<UpdateMetricResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteMetric(id: String): RequestResult<DeleteMetricResponse> {
        return when(val response = request(api.deleteMetric(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteMetricResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
