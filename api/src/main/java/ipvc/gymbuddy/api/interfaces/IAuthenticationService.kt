package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.ActivateRequest
import ipvc.gymbuddy.api.models.requests.LoginRequest
import ipvc.gymbuddy.api.models.requests.RegisterRequest
import ipvc.gymbuddy.api.models.responses.ActivateResponse
import ipvc.gymbuddy.api.models.responses.LoginResponse
import ipvc.gymbuddy.api.models.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthenticationService {
    @POST("login")
    fun login(@Body body: LoginRequest): Call<RequestResult.Success<LoginResponse>>

    @POST("register")
    fun register(@Body body: RegisterRequest): Call<RequestResult.Success<RegisterResponse>>

    @POST("activate")
    fun activate(@Body body: ActivateRequest): Call<RequestResult.Success<ActivateResponse>>
}