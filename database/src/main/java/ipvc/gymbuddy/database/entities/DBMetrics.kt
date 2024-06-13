package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "metrics")
data class DBMetrics (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "creator") val creator: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "value") val value : String,
    @ColumnInfo(name = "date") val date : Date
)
