package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.interfaces.IRoleService
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.responses.RoleResponse

class RoleService : HttpClient<IRoleService>(IRoleService::class.java) {

    suspend fun getRoles(): RequestResult<List<RoleResponse>> {
        return when (val response = request(api.getRoles())) {
            is RequestResult.Success -> {
                val roles = ResponseParser.payload<List<RoleResponse>>(response)
                RequestResult.Success(
                    code = response.code,
                    message = response.message,
                    data = roles
                )
            }
            is RequestResult.Error -> response
        }
    }
}