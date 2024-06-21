package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.contractCategory.CreateContractCategoryRequest
import ipvc.gymbuddy.api.models.responses.contractCategory.CreateContractCategoryResponse
import ipvc.gymbuddy.api.models.responses.contractCategory.DeleteContractCategoryResponse
import ipvc.gymbuddy.api.models.responses.contractCategory.GetAllContractCategoriesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IContractCategoryService {
    @GET("contractCategories")
    fun getContractCategories(): Call<RequestResult.Success<GetAllContractCategoriesResponse>>

    @POST("contractCategories")
    fun createContractCategory(@Body body: CreateContractCategoryRequest): Call<RequestResult.Success<CreateContractCategoryResponse>>

    @DELETE("contractCategories/{id}")
    fun deleteContractCategory(@Path("id") id: String): Call<RequestResult.Success<DeleteContractCategoryResponse>>
}
