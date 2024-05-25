package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.machine.GetAllMachinesResponse
import retrofit2.Call
import retrofit2.http.GET

interface IMachineService {

    @GET("machines")
    fun getMachines(): Call<RequestResult.Success<GetAllMachinesResponse>>
}