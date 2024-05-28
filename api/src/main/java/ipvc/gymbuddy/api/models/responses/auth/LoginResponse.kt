package ipvc.gymbuddy.api.models.responses.auth

import ipvc.gymbuddy.api.models.User

data class LoginResponse (
    val user: User,
    val token: String
)
