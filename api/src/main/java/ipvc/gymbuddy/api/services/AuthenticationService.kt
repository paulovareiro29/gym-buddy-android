package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.interfaces.IAuthenticationService
import retrofit2.awaitResponse

class AuthenticationService: HttpClient<IAuthenticationService>(IAuthenticationService::class.java) {

    suspend fun login() {
        client.login().awaitResponse()
    }
}