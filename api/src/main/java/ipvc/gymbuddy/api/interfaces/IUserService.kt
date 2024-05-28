package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.user.GetAllMetricsResponse
import ipvc.gymbuddy.api.models.responses.user.GetAllUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IUserService {

    @GET("users")
    fun getUsers(): Call<RequestResult.Success<GetAllUsersResponse>>
    @GET("users/{id}/metrics")
    fun getMetrics(@Path("id") id: String): Call<RequestResult.Success<GetAllMetricsResponse>>
}