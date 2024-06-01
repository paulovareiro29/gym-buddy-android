package ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan

import android.app.Application
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanDeleteViewModel (application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val deleteData = trainingPlanDataStore.delete

    fun deleteTrainingPlan(id: String) {
        trainingPlanDataStore.deleteTrainingPlan(id)
    }
}