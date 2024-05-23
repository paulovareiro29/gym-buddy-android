package ipvc.gymbuddy.api.models


data class User (
    val id: String,
    val email: String,
    var role: Role,
    var name: String,
    var address: String,
    val register_code: String,
    val activated: Boolean
)
