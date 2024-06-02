package ipvc.gymbuddy.app.extensions

import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.database.entities.DBCategory

fun Category.toDatabaseModel(): DBCategory {
    return DBCategory(
        id,
        name
    )
}

fun DBCategory.toAPIModel(): Category {
    return Category(
        id,
        name
    )
}