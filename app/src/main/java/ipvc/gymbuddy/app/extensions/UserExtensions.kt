package ipvc.gymbuddy.app.extensions

import ipvc.gymbuddy.api.models.Role
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.database.entities.DBUser
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.UserStatistic
import ipvc.gymbuddy.database.entities.DBUserStatistic

fun User.toDatabaseModel(): DBUser {
    return DBUser(
        this.id,
        this.name,
        this.email,
        this.address,
        this.register_code,
        this.activated,
        Gson().toJson(this.role)
    )
}

fun DBUser.toAPIModel(): User {
    return User(
        this.id,
        Gson().fromJson(this.role, Role::class.java),
        this.name,
        this.email,
        this.address,
        this.register_code,
        this.activated,
    )
}

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