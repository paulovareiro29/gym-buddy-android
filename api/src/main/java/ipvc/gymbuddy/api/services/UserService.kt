package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.interfaces.IUserService
import ipvc.gymbuddy.api.models.responses.user.GetAllUsersResponse

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
}