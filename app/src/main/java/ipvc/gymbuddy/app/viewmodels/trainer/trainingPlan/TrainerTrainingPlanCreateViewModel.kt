package ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan

import android.app.Application
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanCreateViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)

    val postData = trainingPlanDataStore.post

    fun createTrainingPlan(name: String) {
        trainingPlanDataStore.createTrainingPlan(name)
    }
}