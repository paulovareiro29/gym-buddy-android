package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.trainingPlan.CreateTrainingPlanRequest
import ipvc.gymbuddy.api.models.requests.trainingPlan.UpdateTrainingPlanRequest
import ipvc.gymbuddy.api.models.responses.TrainingPlan.CreateTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.TrainingPlan.DeleteTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.TrainingPlan.GetAllTrainingPlansResponse
import ipvc.gymbuddy.api.models.responses.TrainingPlan.GetTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.TrainingPlan.UpdateTrainingPlanResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ITrainingPlanService {
    @GET("plans")
    fun getTrainingPlans(): Call<RequestResult.Success<GetAllTrainingPlansResponse>>

    @GET("plans/{id}")
    fun getTrainingPlan(@Path("id") id: String): Call<RequestResult.Success<GetTrainingPlanResponse>>

    @POST("plans")
    fun createTrainingPlan(@Body body: CreateTrainingPlanRequest): Call<RequestResult.Success<CreateTrainingPlanResponse>>

    @PUT("plans/{id}")
    fun updateTrainingPlan(@Body body: UpdateTrainingPlanRequest, @Path("id") id: String): Call<RequestResult.Success<UpdateTrainingPlanResponse>>

    @DELETE("plans/{id}")
    fun deleteTrainingPlan(@Path("id") id: String): Call<RequestResult.Success<DeleteTrainingPlanResponse>>
}