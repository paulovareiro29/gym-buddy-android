package ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan

import android.app.Application
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val trainingPlansData = trainingPlanDataStore.trainingPlans

    fun getTrainingPlans() {
        trainingPlanDataStore.getTrainingPlans()
    }

}