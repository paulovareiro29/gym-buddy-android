package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import ipvc.gymbuddy.api.models.SimplifiedUser
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.api.models.UserPlan
import ipvc.gymbuddy.database.entities.DBUserPlan

fun UserPlan.toDatabaseModel(): DBUserPlan {
    return DBUserPlan(
        Gson().toJson(user),
        Gson().toJson(plan),
        start_date,
        end_date
    )
}

fun DBUserPlan.toAPIModel(): UserPlan {
    return UserPlan(
        Gson().fromJson(userId, SimplifiedUser::class.java),
        Gson().fromJson(planId, TrainingPlan::class.java),
        startDate,
        endDate
    )
}