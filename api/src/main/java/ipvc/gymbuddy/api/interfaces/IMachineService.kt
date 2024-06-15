package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.machine.CreateMachineRequest
import ipvc.gymbuddy.api.models.requests.machine.UpdateMachineRequest
import ipvc.gymbuddy.api.models.responses.machine.CreateMachineResponse
import ipvc.gymbuddy.api.models.responses.machine.DeleteMachineResponse
import ipvc.gymbuddy.api.models.responses.machine.GetAllMachinesResponse
import ipvc.gymbuddy.api.models.responses.machine.UpdateMachineResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IMachineService {

    @GET("machines")
    fun getMachines(): Call<RequestResult.Success<GetAllMachinesResponse>>

    @POST("machines")
    fun createMachine(@Body body: CreateMachineRequest): Call<RequestResult.Success<CreateMachineResponse>>

    @PUT("machines/{id}")
    fun updateMachine(@Path("id") id: String, @Body body: UpdateMachineRequest): Call<RequestResult.Success<UpdateMachineResponse>>

    @DELETE("machines/{id}")
    fun deleteMachine(@Path("id") id: String): Call<RequestResult.Success<DeleteMachineResponse>>
}