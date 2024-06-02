package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IUserPlanService
import ipvc.gymbuddy.api.models.requests.userPlan.CreateUserPlanRequest
import ipvc.gymbuddy.api.models.responses.userPlan.CreateUserPlanResponse

class UserPlanService : HttpClient<IUserPlanService>(IUserPlanService::class.java) {

    suspend fun createUserPlan(id: String, body: CreateUserPlanRequest): RequestResult<CreateUserPlanResponse> {
        return when (val response = request(api.createUserPlan(body, id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message =  response.message,
                data = ResponseParser.payload<CreateUserPlanResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}