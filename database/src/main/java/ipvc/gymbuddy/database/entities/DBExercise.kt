package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercises")
data class DBExercise(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "machine") val machine: String,
    @ColumnInfo(name = "categories") val categories: String,
)
