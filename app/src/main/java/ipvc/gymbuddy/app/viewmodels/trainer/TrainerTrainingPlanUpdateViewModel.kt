package ipvc.gymbuddy.app.viewmodels.trainer

import android.app.Application
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanUpdateViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val postData = trainingPlanDataStore.post

    fun updateTrainingPlan(id: String, name: String) {
        trainingPlanDataStore.updateTrainingPlan(id, name)
    }
}
