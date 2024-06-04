package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.metric.GetAllMetricsResponse
import ipvc.gymbuddy.api.models.responses.metric.GetMetricResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMetricsService {
    @GET("metrics")
    fun getMetrics(): Call<RequestResult.Success<GetAllMetricsResponse>>

    @GET("metrics/{id}")
    fun getMetric(@Path("id") id: String): Call<RequestResult.Success<GetMetricResponse>>
}