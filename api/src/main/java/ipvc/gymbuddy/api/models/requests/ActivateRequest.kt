package ipvc.gymbuddy.api.models.requests

data class ActivateRequest(
    var email: String,
    var password: String,
    var register_code: String
)
