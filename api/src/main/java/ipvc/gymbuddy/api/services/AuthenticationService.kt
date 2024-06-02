package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.interfaces.IAuthenticationService
import ipvc.gymbuddy.api.models.requests.auth.ActivateRequest
import ipvc.gymbuddy.api.models.requests.auth.LoginRequest
import ipvc.gymbuddy.api.models.requests.auth.RegisterRequest
import ipvc.gymbuddy.api.models.responses.auth.ActivateResponse
import ipvc.gymbuddy.api.models.responses.auth.LoginResponse
import ipvc.gymbuddy.api.models.responses.auth.MeResponse
import ipvc.gymbuddy.api.models.responses.auth.RegisterResponse

class AuthenticationService: HttpClient<IAuthenticationService>(IAuthenticationService::class.java) {
    suspend fun login(body: LoginRequest): RequestResult<LoginResponse> {
        return when (val response = request(api.login(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<LoginResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun register(body: RegisterRequest): RequestResult<RegisterResponse> {
        return when (val response = request(api.register(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<RegisterResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun activate(body: ActivateRequest): RequestResult<ActivateResponse> {
        return when (val response = request(api.activate(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<ActivateResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun me(): RequestResult<MeResponse> {
        return when (val response = request(api.me())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<MeResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}