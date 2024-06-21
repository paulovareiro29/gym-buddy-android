package ipvc.gymbuddy.api.models

data class User (
    var id: String,
    var role: Role,
    var name: String?,
    var avatar: String?,
    var email: String,
    var address: String?,
    val register_code: String,
    val activated: Boolean
)
