package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.trainingPlan.CreateTrainingPlanRequest
import ipvc.gymbuddy.api.models.responses.TrainingPlan.CreateTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.TrainingPlan.GetAllTrainingPlansResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ITrainingPlanService {
    @GET("plans")
    fun getTrainingPlans(): Call<RequestResult.Success<GetAllTrainingPlansResponse>>

    @POST("plans")
    fun createTrainingPlan(@Body body: CreateTrainingPlanRequest): Call<RequestResult.Success<CreateTrainingPlanResponse>>
}