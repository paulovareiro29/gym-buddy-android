package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.userPlan.CreateUserPlanRequest
import ipvc.gymbuddy.api.models.responses.userPlan.CreateUserPlanResponse
import ipvc.gymbuddy.api.models.responses.userPlan.GetAllUserPlanResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IUserPlanService {

    @GET("users/{id}/plans")
    fun getUserPlans(@Path("id") id: String): Call<RequestResult.Success<GetAllUserPlanResponse>>
    @POST("users/{id}/plans")
    fun createUserPlan(@Path("id") id: String, @Body body: CreateUserPlanRequest): Call<RequestResult.Success<CreateUserPlanResponse>>
}