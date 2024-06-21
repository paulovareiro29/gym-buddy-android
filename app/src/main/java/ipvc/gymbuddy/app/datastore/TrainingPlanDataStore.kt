package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.api.models.requests.trainingPlan.CreateTrainingPlanRequest
import ipvc.gymbuddy.api.models.requests.trainingPlan.UpdateTrainingPlanRequest
import ipvc.gymbuddy.api.services.TrainingPlanService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TrainingPlanDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: TrainingPlanDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: TrainingPlanDataStore(context).also { instance = it }
        }
    }

    private val authenticationDataStore = AuthenticationDataStore.getInstance(context)
    var trainingPlans = MutableLiveData<AsyncData<List<TrainingPlan>>>(AsyncData(listOf()))
    var trainingPlan = MutableLiveData<AsyncData<TrainingPlan>>(AsyncData())
    var post = MutableLiveData<AsyncData<CreateTrainingPlanRequest>>(AsyncData())
    var update = MutableLiveData<AsyncData<UpdateTrainingPlanRequest>>(AsyncData())
    var delete = MutableLiveData<AsyncData<Unit>>(AsyncData())

    fun getTrainingPlans() {
        trainingPlans.postValue(AsyncData(trainingPlans.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                trainingPlans.postValue(AsyncData(
                    LocalDatabase.getInstance(context).trainingPlan().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = TrainingPlanService().getTrainingPlans())  {
                is RequestResult.Success -> {
                    val userPlans = filterUserTrainingPlans(response.data.trainingPlans)
                    trainingPlans.postValue(AsyncData(userPlans, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).trainingPlan().insertAll(response.data.trainingPlans.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    trainingPlans.postValue(AsyncData(trainingPlans.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }

    fun getTrainingPlan(id: String): LiveData<AsyncData<TrainingPlan>> {
        trainingPlan.postValue(AsyncData(trainingPlan.value?.data, AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                trainingPlan.postValue(AsyncData(
                    LocalDatabase.getInstance(context).trainingPlan().get(id).toAPIModel(),
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when (val response = TrainingPlanService().getTrainingPlan(id)) {
                is RequestResult.Success -> {
                   trainingPlan.postValue(AsyncData(response.data.trainingPlan, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).trainingPlan().insert(response.data.trainingPlan.toDatabaseModel())
                }
                is RequestResult.Error -> {
                    trainingPlan.postValue(AsyncData(trainingPlan.value?.data, AsyncData.Status.ERROR))
                }
            }
        }
        return trainingPlan
    }

    fun createTrainingPlan(name: String){
        val entity = CreateTrainingPlanRequest(name)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(TrainingPlanService().createTrainingPlan(entity)) {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun updateTrainingPlan(id: String, name: String) {
        val entity = UpdateTrainingPlanRequest(name)
        update.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(TrainingPlanService().updateTrainingPlan(id, entity)) {
                is RequestResult.Success -> update.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> update.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }
            delay(2500)
            update.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun deleteTrainingPlan(id: String) {
        delete.postValue(AsyncData(null, AsyncData.Status.LOADING))
        coroutine.launch {
            when (TrainingPlanService().deleteTrainingPlan(id)) {
                is RequestResult.Success -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                    getTrainingPlans()
                }
                is RequestResult.Error -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.ERROR))
                }
            }
            delay(2500)
            delete.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    private fun filterUserTrainingPlans(allTrainingPlans: List<TrainingPlan>): List<TrainingPlan> {
        val user = authenticationDataStore.user.value
        return if (user != null) {
            allTrainingPlans.filter { it.creator.id == user.id }
        } else {
            listOf()
        }
    }

}