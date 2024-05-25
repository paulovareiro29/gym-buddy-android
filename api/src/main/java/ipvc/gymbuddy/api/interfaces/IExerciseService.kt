package ipvc.gymbuddy.api.interfaces


import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.exercise.CreateExerciseRequest
import ipvc.gymbuddy.api.models.responses.exercise.CreateExerciseResponse
import ipvc.gymbuddy.api.models.responses.exercise.GetAllExercisesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IExerciseService {

    @GET("exercises")
    fun getExercises(): Call<RequestResult.Success<GetAllExercisesResponse>>

    @POST("exercises")
    fun createExercise(@Body body: CreateExerciseRequest): Call<RequestResult.Success<CreateExerciseResponse>>
}