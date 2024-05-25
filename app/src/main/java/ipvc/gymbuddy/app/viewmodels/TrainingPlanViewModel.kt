package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore

class TrainingPlanViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val trainingPlans: LiveData<List<TrainingPlan>> = trainingPlanDataStore.trainingPlans
    val userTrainingPlans: LiveData<List<TrainingPlan>> = trainingPlanDataStore.userTrainingPlans

    fun getTrainingPlans() {
        trainingPlanDataStore.getTrainingPlans()
    }
}