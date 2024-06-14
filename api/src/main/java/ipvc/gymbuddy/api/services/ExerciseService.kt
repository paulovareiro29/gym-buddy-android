package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.interfaces.IExerciseService
import ipvc.gymbuddy.api.models.requests.exercise.CreateExerciseRequest
import ipvc.gymbuddy.api.models.requests.exercise.UpdateExerciseRequest
import ipvc.gymbuddy.api.models.responses.exercise.CreateExerciseResponse
import ipvc.gymbuddy.api.models.responses.exercise.DeleteExerciseResponse
import ipvc.gymbuddy.api.models.responses.exercise.GetAllExercisesResponse
import ipvc.gymbuddy.api.models.responses.exercise.UpdateExerciseResponse

class ExerciseService : HttpClient<IExerciseService>(IExerciseService::class.java) {

    suspend fun getExercises(): RequestResult<GetAllExercisesResponse> {
        return when (val response = request(api.getExercises())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllExercisesResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun createExercise(body: CreateExerciseRequest): RequestResult<CreateExerciseResponse> {
        return when (val response = request(api.createExercise(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateExerciseResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun updateExercise(id: String, body: UpdateExerciseRequest): RequestResult<UpdateExerciseResponse> {
        return when (val response = request(api.updateExercise(id, body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<UpdateExerciseResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteExercise(id: String): RequestResult<DeleteExerciseResponse> {
        return when(val response = request(api.deleteExercise(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteExerciseResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}