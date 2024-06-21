package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.api.models.Exercise
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.database.entities.DBExercise

fun Exercise.toDatabaseModel(): DBExercise {
    return DBExercise(
        id,
        name,
        photo,
        Gson().toJson(machine),
        Gson().toJson(categories)
    )
}

fun DBExercise.toAPIModel(): Exercise {
    return Exercise(
        id,
        name,
        photo,
        Gson().fromJson(machine, Machine::class.java),
        Gson().fromJson(categories, object : TypeToken<List<Category>>() {}.type)
    )
}