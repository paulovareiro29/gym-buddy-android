package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.Success
import ipvc.gymbuddy.api.models.requests.LoginRequest
import ipvc.gymbuddy.api.models.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthenticationService {
    @POST("login")
    fun login(@Body body: LoginRequest): Call<Success<LoginResponse>>
}