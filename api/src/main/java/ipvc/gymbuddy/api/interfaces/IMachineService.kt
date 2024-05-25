package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.machine.CreateMachineRequest
import ipvc.gymbuddy.api.models.responses.machine.CreateMachineResponse
import ipvc.gymbuddy.api.models.responses.machine.GetAllMachinesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IMachineService {

    @GET("machines")
    fun getMachines(): Call<RequestResult.Success<GetAllMachinesResponse>>

    @POST("machines")
    fun createMachine(@Body body: CreateMachineRequest): Call<RequestResult.Success<CreateMachineResponse>>
}