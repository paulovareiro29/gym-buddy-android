package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.ResponseStatus
import ipvc.gymbuddy.api.interfaces.IAuthenticationService
import ipvc.gymbuddy.api.models.requests.LoginRequest
import ipvc.gymbuddy.api.models.responses.LoginResponse

class AuthenticationService: HttpClient<IAuthenticationService>(IAuthenticationService::class.java) {
    suspend fun login(body: LoginRequest): LoginResponse? {
        val response = request(api.login(body))
        return when (response.status) {
            ResponseStatus.Success -> ResponseParser.payload<LoginResponse>(response)
            ResponseStatus.Error -> null
        }
    }
}