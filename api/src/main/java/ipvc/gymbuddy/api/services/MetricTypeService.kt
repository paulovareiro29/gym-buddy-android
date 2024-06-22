package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IMetricTypeService
import ipvc.gymbuddy.api.models.requests.metricTypes.CreateMetricTypeRequest
import ipvc.gymbuddy.api.models.responses.metricTypes.CreateMetricTypeResponse
import ipvc.gymbuddy.api.models.responses.metricTypes.DeleteMetricTypeResponse
import ipvc.gymbuddy.api.models.responses.metricTypes.GetAllMetricTypesResponse

class MetricTypeService  : HttpClient<IMetricTypeService>(IMetricTypeService::class.java) {

    suspend fun getMetricTypes(): RequestResult<GetAllMetricTypesResponse> {
        return when (val response = request(api.getMetricTypes())) {
            is RequestResult.Success -> {
                val parsedData = ResponseParser.payload<GetAllMetricTypesResponse>(response)
                RequestResult.Success(response.code, response.message, parsedData)
            }

            is RequestResult.Error -> response
        }
    }

    suspend fun createMetricType(body: CreateMetricTypeRequest): RequestResult<CreateMetricTypeResponse> {
        return when (val response = request(api.createMetricType(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateMetricTypeResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteMetricType(id: String): RequestResult<DeleteMetricTypeResponse> {
        return when (val response = request(api.deleteMetricType(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteMetricTypeResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
