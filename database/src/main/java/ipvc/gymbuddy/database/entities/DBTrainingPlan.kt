package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "training_plans")
data class DBTrainingPlan(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "creator") val creator: String,
    @ColumnInfo(name = "clients") val clients: String,
)
