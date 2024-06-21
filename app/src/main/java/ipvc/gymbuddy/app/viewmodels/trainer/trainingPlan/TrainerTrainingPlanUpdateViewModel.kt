package ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan

import android.app.Application
import androidx.lifecycle.LiveData
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.datastore.TrainingPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanUpdateViewModel(application: Application) : BaseViewModel(application) {
    private val trainingPlanDataStore = TrainingPlanDataStore.getInstance(application)
    val updateData = trainingPlanDataStore.update

    fun updateTrainingPlan(id: String, name: String) {
        trainingPlanDataStore.updateTrainingPlan(id, name)
    }

    fun getTrainingPlan(id: String): LiveData<AsyncData<TrainingPlan>> {
        return trainingPlanDataStore.getTrainingPlan(id)
    }
}
