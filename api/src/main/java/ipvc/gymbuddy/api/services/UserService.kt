package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IUserService
import ipvc.gymbuddy.api.models.requests.user.UpdateUserRequest
import ipvc.gymbuddy.api.models.responses.user.GetAllUsersResponse
import ipvc.gymbuddy.api.models.responses.user.GetStatisticsResponse
import ipvc.gymbuddy.api.models.responses.user.UpdateUserResponse

class UserService : HttpClient<IUserService>(IUserService::class.java) {

    suspend fun getUsers(): RequestResult<GetAllUsersResponse> {
        return when (val response = request(api.getUsers())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllUsersResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun updateUser(id: String, body: UpdateUserRequest): RequestResult<UpdateUserResponse> {
        return when (val response = request(api.updateUser(id, body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<UpdateUserResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun getStatistics(userId: String): RequestResult<GetStatisticsResponse> {
        return when (val response = request(api.getStatistics(userId))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetStatisticsResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}