package ipvc.gymbuddy.api.services

import android.util.Log
import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IPlanExerciseService
import ipvc.gymbuddy.api.models.responses.planExercise.GetAllPlanExercisesResponse

class PlanExerciseService: HttpClient<IPlanExerciseService>(IPlanExerciseService::class.java) {

    suspend fun getPlanExercises(planId: String): RequestResult<GetAllPlanExercisesResponse> {
        Log.d("PlanExerciseService", "Fetching plan exercises for plan ID: $planId")
        return when (val response = request(api.getPlanExercises(planId))) {
            is RequestResult.Success -> {
                Log.d("PlanExerciseService", "Successfully fetched plan exercises: ${response.data}")
                RequestResult.Success(
                    code = response.code,
                    message = response.message,
                    data = ResponseParser.payload<GetAllPlanExercisesResponse>(response)
                )
            }
            is RequestResult.Error -> {
                Log.e("PlanExerciseService", "Failed to fetch plan exercises: ${response.message}")
                response
            }
        }
    }
}