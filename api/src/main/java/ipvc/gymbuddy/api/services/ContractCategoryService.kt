package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IContractCategoryService
import ipvc.gymbuddy.api.models.responses.contractCategory.GetAllContractCategoriesResponse

class ContractCategoryService : HttpClient<IContractCategoryService>(IContractCategoryService::class.java) {

    suspend fun getContractCategories(): RequestResult<GetAllContractCategoriesResponse> {
        return when (val response = request(api.getContractCategories())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllContractCategoriesResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
