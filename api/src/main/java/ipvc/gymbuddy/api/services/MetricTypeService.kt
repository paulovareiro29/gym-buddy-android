package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IMetricTypeService
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
}
