package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBMachine

@Dao
interface IMachineDao {

    @Query("SELECT * FROM machines")
    suspend fun getAll(): List<DBMachine>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(machines: List<DBMachine>)
}