package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.contract.GetAllContractsResponse
import ipvc.gymbuddy.api.models.requests.contract.CreateContractRequest
import ipvc.gymbuddy.api.models.responses.contract.CreateContractResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IContractService {
    @GET("contracts")
    fun getContracts(@Query("provider_id") providerId: String?): Call<RequestResult.Success<GetAllContractsResponse>>

    @POST("contracts")
    fun createContract(@Body body: CreateContractRequest): Call<RequestResult.Success<CreateContractResponse>>
}