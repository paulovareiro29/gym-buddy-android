package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.auth.ActivateRequest
import ipvc.gymbuddy.api.models.requests.auth.LoginRequest
import ipvc.gymbuddy.api.models.requests.auth.RegisterRequest
import ipvc.gymbuddy.api.models.responses.auth.ActivateResponse
import ipvc.gymbuddy.api.models.responses.auth.LoginResponse
import ipvc.gymbuddy.api.models.responses.auth.MeResponse
import ipvc.gymbuddy.api.models.responses.auth.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IAuthenticationService {
    @POST("login")
    fun login(@Body body: LoginRequest): Call<RequestResult.Success<LoginResponse>>

    @POST("register")
    fun register(@Body body: RegisterRequest): Call<RequestResult.Success<RegisterResponse>>

    @POST("activate")
    fun activate(@Body body: ActivateRequest): Call<RequestResult.Success<ActivateResponse>>

    @GET("me")
    fun me(): Call<RequestResult.Success<MeResponse>>
}