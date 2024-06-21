package ipvc.gymbuddy.app.viewmodels.trainer.planExercise

import android.app.Application
import ipvc.gymbuddy.app.datastore.PlanExerciseDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanExerciseOverviewModel(application: Application) : BaseViewModel(application) {
    private val planExerciseDataStore = PlanExerciseDataStore.getInstance(application)
    val planExerciseData = planExerciseDataStore.planExercises

    fun getPlanExercises(planId: String) {
        planExerciseDataStore.getPlanExercises(planId)
    }

    fun deletePlanExercise(planId: String, id: String){
        planExerciseDataStore.deletePlanExercise(planId, id)
    }
}