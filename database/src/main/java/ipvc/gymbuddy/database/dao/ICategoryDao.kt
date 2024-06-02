package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBCategory

@Dao
interface ICategoryDao {

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<DBCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<DBCategory>)
}