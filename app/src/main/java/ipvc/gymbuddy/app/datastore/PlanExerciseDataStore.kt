package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.api.services.PlanExerciseService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.launch

class PlanExerciseDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: PlanExerciseDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: PlanExerciseDataStore(context).also { instance = it }
        }
    }

    var planExercises = MutableLiveData<AsyncData<List<PlanExercise>>>(AsyncData(listOf()))

    fun getPlanExercises(planId: String) {
        planExercises.postValue(AsyncData(planExercises.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                planExercises.postValue(AsyncData(
                    LocalDatabase.getInstance(context).planExercise().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = PlanExerciseService().getPlanExercises(planId))  {
                is RequestResult.Success -> {
                    planExercises.postValue(AsyncData(response.data.planExercises, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).planExercise().insertAll(response.data.planExercises.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    planExercises.postValue(AsyncData(planExercises.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }
}