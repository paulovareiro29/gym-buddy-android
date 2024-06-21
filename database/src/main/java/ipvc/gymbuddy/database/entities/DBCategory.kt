package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class DBCategory(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)
