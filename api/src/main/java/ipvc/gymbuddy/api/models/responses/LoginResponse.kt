package ipvc.gymbuddy.api.models.responses

import ipvc.gymbuddy.api.models.User

data class LoginResponse (
    val user: User,
    val token: String
)
