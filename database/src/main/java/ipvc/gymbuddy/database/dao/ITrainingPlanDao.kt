package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBTrainingPlan

@Dao
interface ITrainingPlanDao {

    @Query("SELECT * FROM training_plans")
    suspend fun getAll(): List<DBTrainingPlan>

    @Query("SELECT * FROM training_plans WHERE id = :id")
    suspend fun get(id: String): DBTrainingPlan

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plans: List<DBTrainingPlan>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: DBTrainingPlan)
}