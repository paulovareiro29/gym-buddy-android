package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.user.UpdateUserRequest
import ipvc.gymbuddy.api.models.responses.user.GetStatisticsResponse
import ipvc.gymbuddy.api.models.responses.user.GetAllUsersResponse
import ipvc.gymbuddy.api.models.responses.user.UpdateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface IUserService {

    @GET("users")
    fun getUsers(): Call<RequestResult.Success<GetAllUsersResponse>>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: String, @Body body: UpdateUserRequest): Call<RequestResult.Success<UpdateUserResponse>>

    @GET("users/{id}/statistics")
    fun getStatistics(@Path("id") id: String): Call<RequestResult.Success<GetStatisticsResponse>>
}