package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IContractCategoryService
import ipvc.gymbuddy.api.models.requests.contractCategory.CreateContractCategoryRequest
import ipvc.gymbuddy.api.models.responses.contractCategory.CreateContractCategoryResponse
import ipvc.gymbuddy.api.models.responses.contractCategory.DeleteContractCategoryResponse
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

    suspend fun createContractCategory(body: CreateContractCategoryRequest): RequestResult<CreateContractCategoryResponse> {
        return when (val response = request(api.createContractCategory(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateContractCategoryResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteContractCategory(id: String): RequestResult<DeleteContractCategoryResponse> {
        return when (val response = request(api.deleteContractCategory(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteContractCategoryResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
