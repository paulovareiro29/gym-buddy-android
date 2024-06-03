package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IPlanExerciseService
import ipvc.gymbuddy.api.models.responses.planExercise.GetAllPlanExercisesResponse

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
}