package ipvc.gymbuddy.api.interfaces

import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.planExercise.CreatePlanExerciseRequest
import ipvc.gymbuddy.api.models.responses.planExercise.CreatePlanExerciseResponse
import ipvc.gymbuddy.api.models.responses.planExercise.DeletePlanExerciseResponse
import ipvc.gymbuddy.api.models.responses.planExercise.GetAllPlanExercisesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPlanExerciseService {

    @GET("plans/{plan_id}/exercises")
    fun getPlanExercises(@Path("plan_id") planId: String) : Call<RequestResult.Success<GetAllPlanExercisesResponse>>

    @POST("plans/{plan_id}/exercises")
    fun createPlanExercise(@Path("plan_id") planId: String, @Body body: CreatePlanExerciseRequest): Call<RequestResult.Success<CreatePlanExerciseResponse>>

    @DELETE("plans/{plan_id}/exercises/{id}")
    fun deletePlanExercise(@Path("plan_id") planId: String, @Path("id") id: String): Call<RequestResult.Success<DeletePlanExerciseResponse>>
}