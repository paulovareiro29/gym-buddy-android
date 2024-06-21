package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBContractCategory

@Dao
interface IContractCategoryDao {

    @Query("SELECT * FROM contract_categories")
    suspend fun getAll(): List<DBContractCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contractCategories: List<DBContractCategory>)
}