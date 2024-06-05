package ipvc.gymbuddy.app.viewmodels.trainer.planExercise

import android.app.Application
import ipvc.gymbuddy.app.datastore.PlanExerciseDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanExerciseUpdateViewModel(application: Application) : BaseViewModel(application) {
    private val planExerciseDataStore = PlanExerciseDataStore.getInstance(application)
    val updateDate = planExerciseDataStore.update

    fun updatePlanExercise(planId: String, id: String, exerciseId: String, repetitions: Int, sets: Int, restBetweenSets: Int, day: String){
        planExerciseDataStore.updatePlanExercise(planId, id, exerciseId, repetitions, sets, restBetweenSets, day)
    }

    fun getPlanExercises(planId: String) {
        planExerciseDataStore.getPlanExercises(planId)
    }
}