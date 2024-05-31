package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.ITrainingPlanService
import ipvc.gymbuddy.api.models.requests.trainingPlan.CreateTrainingPlanRequest
import ipvc.gymbuddy.api.models.requests.trainingPlan.UpdateTrainingPlanRequest
import ipvc.gymbuddy.api.models.responses.trainingPlan.CreateTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.trainingPlan.DeleteTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.trainingPlan.GetAllTrainingPlansResponse
import ipvc.gymbuddy.api.models.responses.trainingPlan.GetTrainingPlanResponse
import ipvc.gymbuddy.api.models.responses.trainingPlan.UpdateTrainingPlanResponse

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

    suspend fun getTrainingPlan(id: String): RequestResult<GetTrainingPlanResponse> {
        return when (val response = request(api.getTrainingPlan(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetTrainingPlanResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun createTrainingPlan(body: CreateTrainingPlanRequest): RequestResult<CreateTrainingPlanResponse> {
        return when(val response = request(api.createTrainingPlan(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateTrainingPlanResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun updateTrainingPlan(id: String, body: UpdateTrainingPlanRequest): RequestResult<UpdateTrainingPlanResponse> {
        return when(val response = request(api.updateTrainingPlan(body, id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<UpdateTrainingPlanResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteTrainingPlan(id: String): RequestResult<DeleteTrainingPlanResponse> {
        return when(val response = request(api.deleteTrainingPlan(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteTrainingPlanResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}