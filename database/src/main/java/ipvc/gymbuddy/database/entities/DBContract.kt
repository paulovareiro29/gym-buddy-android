package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contracts")
data class DBContract(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "beneficiary") val beneficiary: String,
    @ColumnInfo(name = "provider") val provider: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "start_date") val start_date: String,
    @ColumnInfo(name = "end_date") val end_date: String,
)
