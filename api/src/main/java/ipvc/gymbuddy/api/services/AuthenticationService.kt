package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.Error
import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.ResponseStatus
import ipvc.gymbuddy.api.interfaces.IAuthenticationService
import ipvc.gymbuddy.api.models.requests.LoginRequest
import ipvc.gymbuddy.api.models.responses.LoginResponse

class AuthenticationService: HttpClient<IAuthenticationService>(IAuthenticationService::class.java) {
    suspend fun login(body: LoginRequest): Boolean {
        val response = request(api.login(body))
        return when (response.status) {
            ResponseStatus.Success -> {
                val data = ResponseParser.payload<LoginResponse>(response)
                println("User logged in successfully: ${data.user.email}")
                return true
            }
            ResponseStatus.Error -> {
                val data = response as Error
                println("Failed to login: ${data.message}")
                false
            }
        }
    }
}