package ipvc.gymbuddy.app.extensions

import ipvc.gymbuddy.api.models.ContractCategory
import ipvc.gymbuddy.database.entities.DBContractCategory

fun ContractCategory.toDatabaseModel(): DBContractCategory {
    return DBContractCategory(
        id,
        name
    )
}

fun DBContractCategory.toAPIModel(): ContractCategory {
    return ContractCategory(
        id,
        name
    )
}