package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class DBUser(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "register_code") val register_code: String,
    @ColumnInfo(name = "activated") val activated: Boolean,
    @ColumnInfo(name = "role") val role: String,
)
