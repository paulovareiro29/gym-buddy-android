package ipvc.gymbuddy.api.interfaces

import retrofit2.Call
import retrofit2.http.POST

interface IAuthenticationService {
    @POST("login")
    fun login(): Call<String>
}