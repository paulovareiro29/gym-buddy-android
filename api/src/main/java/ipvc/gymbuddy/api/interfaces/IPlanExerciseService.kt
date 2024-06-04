package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.planExercise.GetAllPlanExercisesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IPlanExerciseService {

    @GET("plans/{plan_id}/exercises")
    fun getPlanExercises(@Path("plan_id") planId: String) : Call<RequestResult.Success<GetAllPlanExercisesResponse>>
}