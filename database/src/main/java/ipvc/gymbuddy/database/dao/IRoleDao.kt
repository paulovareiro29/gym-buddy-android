package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBRole

@Dao
interface IRoleDao {

    @Query("SELECT * FROM roles")
    suspend fun getAll(): List<DBRole>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(roles: List<DBRole>)
}