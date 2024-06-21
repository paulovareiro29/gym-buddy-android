package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "contract_categories")
data class DBContractCategory (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)