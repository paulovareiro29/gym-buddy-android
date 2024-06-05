package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IPlanExerciseService
import ipvc.gymbuddy.api.models.requests.planExercise.CreatePlanExerciseRequest
import ipvc.gymbuddy.api.models.requests.planExercise.UpdatePlanExerciseRequest
import ipvc.gymbuddy.api.models.responses.planExercise.CreatePlanExerciseResponse
import ipvc.gymbuddy.api.models.responses.planExercise.DeletePlanExerciseResponse
import ipvc.gymbuddy.api.models.responses.planExercise.GetAllPlanExercisesResponse
import ipvc.gymbuddy.api.models.responses.planExercise.UpdatePlanExerciseResponse

class PlanExerciseService: HttpClient<IPlanExerciseService>(IPlanExerciseService::class.java) {

    suspend fun getPlanExercises(planId: String): RequestResult<GetAllPlanExercisesResponse> {
        return when (val response = request(api.getPlanExercises(planId))) {
            is RequestResult.Success -> {
                RequestResult.Success(
                    code = response.code,
                    message = response.message,
                    data = ResponseParser.payload<GetAllPlanExercisesResponse>(response)
                )
            }
            is RequestResult.Error -> {
                response
            }
        }
    }

    suspend fun createPlanExercise(planId: String, body: CreatePlanExerciseRequest): RequestResult<CreatePlanExerciseResponse> {
        return when(val response = request(api.createPlanExercise(planId, body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreatePlanExerciseResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun updatePlanExercise(planId: String, id: String, body: UpdatePlanExerciseRequest): RequestResult<UpdatePlanExerciseResponse> {
        return when(val response = request(api.updatePlanExercise(planId, id, body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<UpdatePlanExerciseResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deletePlanExercise(planId: String, id: String): RequestResult<DeletePlanExerciseResponse> {
        return when(val response = request(api.deletePlanExercise(planId, id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeletePlanExerciseResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}