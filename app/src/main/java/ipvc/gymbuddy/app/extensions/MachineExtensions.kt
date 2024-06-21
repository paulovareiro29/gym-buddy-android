package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.database.entities.DBMachine

fun Machine.toDatabaseModel(): DBMachine {
    return DBMachine(
        id,
        name,
        photo,
        Gson().toJson(categories)
    )
}

fun DBMachine.toAPIModel(): Machine {
    return Machine(
        id,
        name,
        photo,
        Gson().fromJson(categories, object : TypeToken<List<Category>>() {}.type)
    )
}