package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan_exercises")
data class DBPlanExercise (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "plan") val plan: String,
    @ColumnInfo(name = "exercise") val exercise: String,
    @ColumnInfo(name = "repetitions") val repetitions: Int,
    @ColumnInfo(name = "sets") val sets: Int,
    @ColumnInfo(name = "rest_between_sets") val rest_between_sets: Int,
    @ColumnInfo(name = "day") val day: String,
)