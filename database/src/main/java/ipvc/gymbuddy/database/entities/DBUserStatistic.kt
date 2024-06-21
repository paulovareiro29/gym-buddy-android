package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_statistics")
data class DBUserStatistic(
    @PrimaryKey val user_id: String,
    @ColumnInfo(name = "number_of_contracts") val number_of_contracts: Int,
    @ColumnInfo(name = "number_of_created_plans") val number_of_created_plans: Int,
    @ColumnInfo(name = "number_of_associated_plans") val number_of_associated_plans: Int,
    @ColumnInfo(name = "number_of_metrics") val number_of_metrics : Int
)
