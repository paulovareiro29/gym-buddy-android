package ipvc.gymbuddy.app.viewmodels.trainer

import android.app.Application
import androidx.lifecycle.LiveData
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainingPlanOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val trainingPlansData = trainingPlanDataStore.trainingPlans
    val userTrainingPlans: LiveData<List<TrainingPlan>> = trainingPlanDataStore.userTrainingPlans

    fun getTrainingPlans() {
        trainingPlanDataStore.getTrainingPlans()
    }
}