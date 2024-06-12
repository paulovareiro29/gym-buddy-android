package ipvc.gymbuddy.app.extensions

import ipvc.gymbuddy.api.models.MetricType
import ipvc.gymbuddy.database.entities.DBMetricTypes

fun MetricType.toDatabaseModel(): DBMetricTypes {
    return DBMetricTypes(
        id,
        name
    )
}

fun DBMetricTypes.toAPIModel(): MetricType {
    return MetricType(
        id,
        name
    )
}