package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.role.GetAllRolesResponse
import retrofit2.Call
import retrofit2.http.GET

interface IRoleService {

    @GET("roles")
    fun getRoles(): Call<RequestResult.Success<GetAllRolesResponse>>
}