package ipvc.gymbuddy.app.extensions

import ipvc.gymbuddy.api.models.UserStatistic
import ipvc.gymbuddy.database.entities.DBUserStatistic

fun UserStatistic.toDatabaseModel(): DBUserStatistic {
    return DBUserStatistic(
        user_id,
        number_of_contracts,
        number_of_created_plans,
        number_of_associated_plans,
        number_of_metrics,
    )
}

fun DBUserStatistic.toAPIModel(): UserStatistic {
    return UserStatistic(
        user_id,
        number_of_contracts,
        number_of_created_plans,
        number_of_associated_plans,
        number_of_metrics,
    )
}