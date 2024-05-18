package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.interfaces.IRoleService
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.role.GetAllRolesResponse

class RoleService : HttpClient<IRoleService>(IRoleService::class.java) {

    suspend fun getRoles(): RequestResult<GetAllRolesResponse> {
        return when (val response = request(api.getRoles())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllRolesResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}