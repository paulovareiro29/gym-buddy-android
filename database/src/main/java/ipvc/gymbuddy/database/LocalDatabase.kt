package ipvc.gymbuddy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ipvc.gymbuddy.database.dao.ICategoryDao
import ipvc.gymbuddy.database.dao.IContractDao
import ipvc.gymbuddy.database.dao.IExerciseDao
import ipvc.gymbuddy.database.dao.IMachineDao
import ipvc.gymbuddy.database.dao.IPlanExerciseDao
import ipvc.gymbuddy.database.dao.IRoleDao
import ipvc.gymbuddy.database.dao.ITrainingPlanDao
import ipvc.gymbuddy.database.dao.IUserDao
import ipvc.gymbuddy.database.entities.DBCategory
import ipvc.gymbuddy.database.entities.DBContract
import ipvc.gymbuddy.database.entities.DBExercise
import ipvc.gymbuddy.database.entities.DBMachine
import ipvc.gymbuddy.database.entities.DBPlanExercise
import ipvc.gymbuddy.database.entities.DBRole
import ipvc.gymbuddy.database.entities.DBTrainingPlan
import ipvc.gymbuddy.database.entities.DBUser
import ipvc.gymbuddy.database.entities.DBUserStatistic

@Database(entities = [
    DBUser::class,
    DBUserStatistic::class,
    DBRole::class,
    DBCategory::class,
    DBMachine::class,
    DBExercise::class,
    DBTrainingPlan::class,
    DBContract::class,
    DBPlanExercise::class
], version = 2)
abstract class LocalDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "gym-buddy"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun user(): IUserDao
    abstract fun role(): IRoleDao
    abstract fun category(): ICategoryDao
    abstract fun machine(): IMachineDao
    abstract fun exercise(): IExerciseDao
    abstract fun trainingPlan(): ITrainingPlanDao
    abstract fun contract(): IContractDao
    abstract fun planExercise(): IPlanExerciseDao
}