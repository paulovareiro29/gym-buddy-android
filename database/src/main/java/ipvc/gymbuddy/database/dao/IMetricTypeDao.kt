package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBMetricTypes

@Dao
interface IMetricTypeDao {
    @Query("SELECT * FROM metricTypes")
    suspend fun getAll(): List<DBMetricTypes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(metricTypes: List<DBMetricTypes>)
}