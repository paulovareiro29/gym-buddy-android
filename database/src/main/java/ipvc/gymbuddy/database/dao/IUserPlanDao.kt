package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBUserPlan

@Dao
interface IUserPlanDao {

    @Query("SELECT * FROM userPlans")
    suspend fun getAll(): List<DBUserPlan>

    @Query("SELECT * FROM userPlans WHERE user_id = :userId AND plan_id = :planId")
    suspend fun get(userId: String, planId: String): DBUserPlan?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userPlans: List<DBUserPlan>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPlan: DBUserPlan)
}
