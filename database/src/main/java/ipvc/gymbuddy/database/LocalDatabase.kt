package ipvc.gymbuddy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ipvc.gymbuddy.database.dao.IUserDao
import ipvc.gymbuddy.database.entities.DBUser

@Database(entities = [
    DBUser::class
], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local-database-2"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun user(): IUserDao
}