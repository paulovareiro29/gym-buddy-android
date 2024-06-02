package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.api.models.Contract
import ipvc.gymbuddy.api.models.SimplifiedUser
import ipvc.gymbuddy.database.entities.DBContract

fun Contract.toDatabaseModel(): DBContract {
    return DBContract(
        id,
        Gson().toJson(beneficiary),
        Gson().toJson(provider),
        Gson().toJson(category),
        start_date,
        end_date
    )
}

fun DBContract.toAPIModel(): Contract {
    return Contract(
        id,
        Gson().fromJson(beneficiary, SimplifiedUser::class.java),
        Gson().fromJson(provider, SimplifiedUser::class.java),
        Gson().fromJson(category, Category::class.java),
        start_date,
        end_date
    )
}