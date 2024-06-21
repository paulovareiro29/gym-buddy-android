package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBMetrics

@Dao
interface IMetricDao {
    @Query("SELECT * FROM metrics")
    suspend fun getAll(): List<DBMetrics>

    @Query("SELECT * FROM metrics WHERE id = :id")
    suspend fun get(id: String): DBMetrics

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(metrics: List<DBMetrics>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(metrics: DBMetrics)
}