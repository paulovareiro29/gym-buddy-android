package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.user.GetAllUsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface IUserService {

    @GET("users")
    fun getUsers(): Call<RequestResult.Success<GetAllUsersResponse>>
}