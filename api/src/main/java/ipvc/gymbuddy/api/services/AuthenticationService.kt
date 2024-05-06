package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.ResponseStatus
import ipvc.gymbuddy.api.interfaces.IAuthenticationService
import ipvc.gymbuddy.api.models.requests.ActivateRequest
import ipvc.gymbuddy.api.models.requests.LoginRequest
import ipvc.gymbuddy.api.models.responses.ActivateResponse
import ipvc.gymbuddy.api.models.responses.LoginResponse

class AuthenticationService: HttpClient<IAuthenticationService>(IAuthenticationService::class.java) {
    suspend fun login(body: LoginRequest): LoginResponse? {
        val response = request(api.login(body))
        return when (response.status) {
            ResponseStatus.Success -> ResponseParser.payload<LoginResponse>(response)
            ResponseStatus.Error -> null
        }
    }

    suspend fun activate(body: ActivateRequest): ActivateResponse? {
        val response = request(api.activate(body))
        return when (response.status) {
            ResponseStatus.Success -> ResponseParser.payload<ActivateResponse>(response)
            ResponseStatus.Error -> null
        }
    }
}