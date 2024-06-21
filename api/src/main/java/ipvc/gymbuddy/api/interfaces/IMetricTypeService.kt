package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.metricTypes.GetAllMetricTypesResponse
import retrofit2.Call
import retrofit2.http.GET

interface IMetricTypeService {

    @GET("metricTypes")
    fun getMetricTypes(): Call<RequestResult.Success<GetAllMetricTypesResponse>>
}