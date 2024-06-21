package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBExercise

@Dao
interface IExerciseDao {

    @Query("SELECT * FROM exercises")
    suspend fun getAll(): List<DBExercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<DBExercise>)
}