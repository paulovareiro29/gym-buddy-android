package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IContractService
import ipvc.gymbuddy.api.models.responses.contract.GetAllContractsResponse

class ContractService: HttpClient<IContractService>(IContractService::class.java) {

    suspend fun getContract(providerId: String): RequestResult<GetAllContractsResponse> {
        return when (val response = request(api.getContracts(providerId))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllContractsResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}