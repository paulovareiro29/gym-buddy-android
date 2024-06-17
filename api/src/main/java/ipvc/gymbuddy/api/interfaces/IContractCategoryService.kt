package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.contractCategory.GetAllContractCategoriesResponse
import retrofit2.Call
import retrofit2.http.GET

interface IContractCategoryService {
    @GET("contractCategories")
    fun getContractCategories(): Call<RequestResult.Success<GetAllContractCategoriesResponse>>
}
