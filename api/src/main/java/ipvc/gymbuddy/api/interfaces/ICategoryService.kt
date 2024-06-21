package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.category.CreateCategoryRequest
import ipvc.gymbuddy.api.models.responses.category.CreateCategoryResponse
import ipvc.gymbuddy.api.models.responses.category.DeleteCategoryResponse
import ipvc.gymbuddy.api.models.responses.category.GetAllCategoriesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ICategoryService {

    @GET("categories")
    fun getCategories(): Call<RequestResult.Success<GetAllCategoriesResponse>>

    @POST("categories")
    fun createCategory(@Body body: CreateCategoryRequest): Call<RequestResult.Success<CreateCategoryResponse>>

    @DELETE("categories/{id}")
    fun deleteCategory(@Path("id") id: String): Call<RequestResult.Success<DeleteCategoryResponse>>
}