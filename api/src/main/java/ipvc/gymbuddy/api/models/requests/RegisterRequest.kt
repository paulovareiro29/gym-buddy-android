package ipvc.gymbuddy.api.models.requests

data class RegisterRequest(
    var name: String,
    var email: String,
    var role_id: String
)
