package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.api.models.MetricType
import ipvc.gymbuddy.api.models.SimplifiedUser
import ipvc.gymbuddy.database.entities.DBMetrics

fun Metric.toDatabaseModel(): DBMetrics {
    return DBMetrics(
        id,
        Gson().toJson(user),
        Gson().toJson(creator),
        Gson().toJson(type),
        value,
        date
    )
}

fun DBMetrics.toAPIModel(): Metric {
    return Metric(
        id,
        Gson().fromJson(user, SimplifiedUser::class.java),
        Gson().fromJson(type, MetricType::class.java),
        Gson().fromJson(creator, SimplifiedUser::class.java),
        value,
        date
    )
}