package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBContract
import ipvc.gymbuddy.database.entities.DBTrainingPlan

@Dao
interface IContractDao {

    @Query("SELECT * FROM contracts")
    suspend fun getAll(): List<DBContract>

    @Query("SELECT * FROM contracts WHERE id = :id")
    suspend fun get(id: String): DBContract

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contracts: List<DBContract>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contract: DBContract)
}