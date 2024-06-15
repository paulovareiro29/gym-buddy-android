package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.interfaces.ICategoryService
import ipvc.gymbuddy.api.models.requests.category.CreateCategoryRequest
import ipvc.gymbuddy.api.models.responses.category.CreateCategoryResponse
import ipvc.gymbuddy.api.models.responses.category.DeleteCategoryResponse
import ipvc.gymbuddy.api.models.responses.category.GetAllCategoriesResponse

class CategoryService : HttpClient<ICategoryService>(ICategoryService::class.java) {

    suspend fun getCategories(): RequestResult<GetAllCategoriesResponse> {
        return when (val response = request(api.getCategories())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllCategoriesResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun createCategory(body: CreateCategoryRequest): RequestResult<CreateCategoryResponse> {
        return when (val response = request(api.createCategory(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateCategoryResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteCategory(id: String): RequestResult<DeleteCategoryResponse> {
        return when (val response = request(api.deleteCategory(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteCategoryResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}