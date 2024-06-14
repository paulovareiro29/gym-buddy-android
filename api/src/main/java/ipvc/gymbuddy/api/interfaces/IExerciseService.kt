package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.exercise.CreateExerciseRequest
import ipvc.gymbuddy.api.models.requests.exercise.UpdateExerciseRequest
import ipvc.gymbuddy.api.models.responses.exercise.CreateExerciseResponse
import ipvc.gymbuddy.api.models.responses.exercise.GetAllExercisesResponse
import ipvc.gymbuddy.api.models.responses.exercise.UpdateExerciseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IExerciseService {

    @GET("exercises")
    fun getExercises(): Call<RequestResult.Success<GetAllExercisesResponse>>

    @POST("exercises")
    fun createExercise(@Body body: CreateExerciseRequest): Call<RequestResult.Success<CreateExerciseResponse>>

    @PUT("exercises/{id}")
    fun updateExercise(@Path("id") id: String, @Body body: UpdateExerciseRequest): Call<RequestResult.Success<UpdateExerciseResponse>>
}