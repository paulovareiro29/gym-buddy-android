package ipvc.gymbuddy.api.models.responses.user

import ipvc.gymbuddy.api.models.User

data class GetAllUsersResponse (
    val users: List<User>
)