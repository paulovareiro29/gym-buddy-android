package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.ITrainingPlanService
import ipvc.gymbuddy.api.models.responses.TrainingPlan.GetAllTrainingPlansResponse

class TrainingPlanService : HttpClient<ITrainingPlanService>(ITrainingPlanService::class.java){

    suspend fun getTrainingPlans(): RequestResult<GetAllTrainingPlansResponse> {
        return when (val response = request(api.getTrainingPlans())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllTrainingPlansResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}