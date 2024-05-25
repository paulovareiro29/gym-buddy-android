package ipvc.gymbuddy.app.viewmodels.trainer

import android.app.Application
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainingPlanOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val trainingPlansData = trainingPlanDataStore.trainingPlans


    fun getTrainingPlans() {
        trainingPlanDataStore.getTrainingPlans()
    }
}