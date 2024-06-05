package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.api.models.requests.planExercise.CreatePlanExerciseRequest
import ipvc.gymbuddy.api.models.requests.planExercise.UpdatePlanExerciseRequest
import ipvc.gymbuddy.api.services.PlanExerciseService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlanExerciseDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: PlanExerciseDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: PlanExerciseDataStore(context).also { instance = it }
        }
    }

    private val _planExercises = MutableLiveData<AsyncData<List<PlanExercise>>>(AsyncData(listOf()))
    val planExercises: LiveData<AsyncData<List<PlanExercise>>> get() = _planExercises
    val post = MutableLiveData<AsyncData<CreatePlanExerciseRequest>>(AsyncData())
    var update = MutableLiveData<AsyncData<UpdatePlanExerciseRequest>>(AsyncData())
    val delete = MutableLiveData<AsyncData<Unit>>(AsyncData())

    fun getPlanExercises(planId: String): LiveData<List<PlanExercise>> {
        _planExercises.postValue(AsyncData(_planExercises.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                _planExercises.postValue(AsyncData(
                    LocalDatabase.getInstance(context).planExercise().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = PlanExerciseService().getPlanExercises(planId)) {
                is RequestResult.Success -> {
                    _planExercises.postValue(AsyncData(response.data.planExercises, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).planExercise().insertAll(response.data.planExercises.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    _planExercises.postValue(AsyncData(_planExercises.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
        return _planExercises.map { it.data ?: emptyList() }
    }


    fun createPlanExercise(planId:String, exerciseId: String, repetitions: Int, sets: Int, restBetweenSets: Int, day: String){
        val entity = CreatePlanExerciseRequest(exerciseId, repetitions, sets, restBetweenSets, day)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(PlanExerciseService().createPlanExercise(planId,entity)) {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun updatePlanExercise(planId: String, id: String, exerciseId: String, repetitions: Int, sets: Int, restBetweenSets: Int, day: String) {
        val entity = UpdatePlanExerciseRequest(exerciseId, repetitions, sets, restBetweenSets, day)
        update.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(PlanExerciseService().updatePlanExercise(planId, id, entity)) {
                is RequestResult.Success -> update.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> update.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }
            delay(2500)
            update.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun deletePlanExercise(planId: String, id: String) {
        delete.postValue(AsyncData(null, AsyncData.Status.LOADING))
        coroutine.launch {
            when (PlanExerciseService().deletePlanExercise(planId, id)) {
                is RequestResult.Success -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                    getPlanExercises(planId)
                }
                is RequestResult.Error -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.ERROR))
                }
            }
            delay(2500)
            delete.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}