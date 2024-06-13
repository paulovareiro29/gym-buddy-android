package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.metric.CreateMetricRequest
import ipvc.gymbuddy.api.models.requests.metric.UpdateMetricRequest
import ipvc.gymbuddy.api.models.responses.metric.CreateMetricResponse
import ipvc.gymbuddy.api.models.responses.metric.DeleteMetricResponse
import ipvc.gymbuddy.api.models.responses.metric.GetAllMetricsResponse
import ipvc.gymbuddy.api.models.responses.metric.GetMetricResponse
import ipvc.gymbuddy.api.models.responses.metric.UpdateMetricResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface IMetricsService {
    @GET("metrics")
    fun getMetrics(@Query("user_id") userId: String): Call<RequestResult.Success<GetAllMetricsResponse>>

    @GET("metrics/{id}")
    fun getMetric(@Path("id") id: String): Call<RequestResult.Success<GetMetricResponse>>

    @POST("metrics")
    fun createMetric(@Body body: CreateMetricRequest): Call<RequestResult.Success<CreateMetricResponse>>

    @PUT("metrics/{id}")
    fun updateMetric(@Body body: UpdateMetricRequest, @Path("id") id: String): Call<RequestResult.Success<UpdateMetricResponse>>
    @DELETE("metrics/{id}")
    fun deleteMetric(@Path("id") id: String): Call<RequestResult.Success<DeleteMetricResponse>>
}