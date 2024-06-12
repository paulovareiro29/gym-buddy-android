package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metricTypes")
class DBMetricTypes(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)