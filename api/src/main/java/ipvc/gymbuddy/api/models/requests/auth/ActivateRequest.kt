package ipvc.gymbuddy.api.models.requests.auth

data class ActivateRequest(
    var email: String,
    var password: String,
    var register_code: String
)
