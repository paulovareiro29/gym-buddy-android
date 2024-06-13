package ipvc.gymbuddy.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date

@Entity(tableName = "userPlans", primaryKeys = ["user_id", "plan_id"])
data class DBUserPlan(
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "plan_id") val planId: String,
    @ColumnInfo(name = "start_date") val startDate: Date,
    @ColumnInfo(name = "end_date") val endDate: Date
)