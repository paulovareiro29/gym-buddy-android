package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.metricTypes.CreateMetricTypeRequest
import ipvc.gymbuddy.api.models.responses.metricTypes.CreateMetricTypeResponse
import ipvc.gymbuddy.api.models.responses.metricTypes.DeleteMetricTypeResponse
import ipvc.gymbuddy.api.models.responses.metricTypes.GetAllMetricTypesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IMetricTypeService {

    @GET("metricTypes")
    fun getMetricTypes(): Call<RequestResult.Success<GetAllMetricTypesResponse>>

    @POST("metricTypes")
    fun createMetricType(@Body body: CreateMetricTypeRequest): Call<RequestResult.Success<CreateMetricTypeResponse>>

    @DELETE("metricTypes/{id}")
    fun deleteMetricType(@Path("id") id: String): Call<RequestResult.Success<DeleteMetricTypeResponse>>
}