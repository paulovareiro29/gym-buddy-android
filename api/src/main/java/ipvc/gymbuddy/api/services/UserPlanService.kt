package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IUserPlanService
import ipvc.gymbuddy.api.models.requests.userPlan.CreateUserPlanRequest
import ipvc.gymbuddy.api.models.responses.userPlan.CreateUserPlanResponse
import ipvc.gymbuddy.api.models.responses.userPlan.GetAllUserPlanResponse

class UserPlanService : HttpClient<IUserPlanService>(IUserPlanService::class.java) {
    suspend fun getUserPlans(userId : String): RequestResult<GetAllUserPlanResponse> {
        return when (val response = request(api.getUserPlans(userId))) {
            is RequestResult.Success -> {
                val parsedData = ResponseParser.payload<GetAllUserPlanResponse>(response)
                RequestResult.Success(response.code, response.message, parsedData)
            }
            is RequestResult.Error -> response
        }
    }

    suspend fun createUserPlan(id: String, body: CreateUserPlanRequest): RequestResult<CreateUserPlanResponse> {
        return when (val response = request(api.createUserPlan(id, body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message =  response.message,
                data = ResponseParser.payload<CreateUserPlanResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}