package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.api.services.TrainingPlanService
import ipvc.gymbuddy.app.core.AsyncData
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

    fun getTrainingPlans() {
        trainingPlans.postValue(AsyncData(trainingPlans.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            when(val response = TrainingPlanService().getTrainingPlans())  {
                is RequestResult.Success -> {
                    val allTrainingPlans = response.data.trainingPlans
                    val userPlans = filterUserTrainingPlans(allTrainingPlans)
                    trainingPlans.postValue(AsyncData(userPlans, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    trainingPlans.postValue(AsyncData(trainingPlans.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
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