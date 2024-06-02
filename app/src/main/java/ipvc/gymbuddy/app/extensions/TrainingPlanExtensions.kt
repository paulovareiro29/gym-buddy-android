package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ipvc.gymbuddy.api.models.SimplifiedUser
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.database.entities.DBTrainingPlan

fun TrainingPlan.toDatabaseModel(): DBTrainingPlan {
    return DBTrainingPlan(
        id,
        name,
        Gson().toJson(creator),
        Gson().toJson(clients)
    )
}

fun DBTrainingPlan.toAPIModel(): TrainingPlan {
    return TrainingPlan(
        id,
        name,
        Gson().fromJson(creator, SimplifiedUser::class.java),
        Gson().fromJson(clients, object : TypeToken<List<SimplifiedUser>>() {}.type)
    )
}