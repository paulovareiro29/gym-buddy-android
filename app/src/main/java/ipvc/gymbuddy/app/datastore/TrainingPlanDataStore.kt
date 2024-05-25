package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.api.services.TrainingPlanService
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
    var trainingPlans = MutableLiveData<List<TrainingPlan>>(listOf())
    var userTrainingPlans = MutableLiveData<List<TrainingPlan>>(listOf())
    var trainingPlanStatus = MutableLiveData("idle")

    fun getTrainingPlans() {
        coroutine.launch {
            trainingPlanStatus.postValue("loading")
            when (val response = TrainingPlanService().getTrainingPlans()) {
                is RequestResult.Success -> {
                    val allTrainingPlans = response.data.trainingPlans
                    trainingPlans.postValue(allTrainingPlans)
                    filterUserTrainingPlans(allTrainingPlans)
                    trainingPlanStatus.postValue("success")
                }
                is RequestResult.Error -> {
                    trainingPlans.postValue(listOf())
                    userTrainingPlans.postValue(listOf())
                    trainingPlanStatus.postValue("error")
                }
            }
        }
    }

    private fun filterUserTrainingPlans(allTrainingPlans: List<TrainingPlan>) {
        val user = authenticationDataStore.user.value
        if (user != null) {
            val userPlans = allTrainingPlans.filter { it.creatorId == user}
            userTrainingPlans.postValue(userPlans)
        } else {
            userTrainingPlans.postValue(listOf())
        }
    }

}