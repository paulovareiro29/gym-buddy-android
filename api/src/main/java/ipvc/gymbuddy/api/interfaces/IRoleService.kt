package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.models.responses.RoleResponse
import retrofit2.Call
import retrofit2.http.GET

interface IRoleService {

    @GET("/roles")
    fun getRoles(): Call<List<RoleResponse>>
}