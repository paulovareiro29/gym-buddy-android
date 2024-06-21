package ipvc.gymbuddy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.gymbuddy.database.entities.DBUser
import ipvc.gymbuddy.database.entities.DBUserStatistic

@Dao
interface IUserDao {

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<DBUser>

    @Query("SELECT * FROM user_statistics WHERE user_id = :user_id")
    suspend fun getStatistics(user_id: String): DBUserStatistic

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistic(statistic: DBUserStatistic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<DBUser>)
}