package ipvc.gymbuddy.api.models.responses.role

import ipvc.gymbuddy.api.models.Role

data class GetAllRolesResponse (
    val roles: List<Role>
)