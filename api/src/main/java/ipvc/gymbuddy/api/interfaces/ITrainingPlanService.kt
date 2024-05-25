package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.TrainingPlan.GetAllTrainingPlansResponse
import retrofit2.Call
import retrofit2.http.GET

interface ITrainingPlanService {
    @GET("plans")
    fun getTrainingPlans(): Call<RequestResult.Success<GetAllTrainingPlansResponse>>
}