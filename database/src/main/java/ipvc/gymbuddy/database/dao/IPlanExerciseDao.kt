package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBPlanExercise

@Dao
interface IPlanExerciseDao {

    @Query("SELECT * FROM plan_exercises")
    suspend fun getAll(): List<DBPlanExercise>

    @Query("SELECT * FROM plan_exercises WHERE id = :id")
    suspend fun get(id: String): DBPlanExercise

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(planExercises: List<DBPlanExercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planExercise: DBPlanExercise)
}

