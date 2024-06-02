package ipvc.gymbuddy.app.extensions

import ipvc.gymbuddy.api.models.Role
import ipvc.gymbuddy.database.entities.DBRole

fun Role.toDatabaseModel(): DBRole {
    return DBRole(
        id,
        name
    )
}

fun DBRole.toAPIModel(): Role {
    return Role(
        id,
        name
    )
}