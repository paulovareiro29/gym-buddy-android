package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.contract.GetAllContractsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IContractService {
    @GET("contracts")
    fun getContracts(@Query("provider_id") providerId: String?): Call<RequestResult.Success<GetAllContractsResponse>>
}