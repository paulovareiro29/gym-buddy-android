package ipvc.gymbuddy.app.viewmodels.trainer.planExercise

import android.app.Application
import androidx.lifecycle.LiveData
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.datastore.ExerciseDataStore
import ipvc.gymbuddy.app.datastore.PlanExerciseDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerTrainingPlanExerciseCreateViewModel(application: Application) : BaseViewModel(application) {
    private val planExerciseDataStore = PlanExerciseDataStore.getInstance(application)
    private val exerciseDataStore = ExerciseDataStore.getInstance(application)
    val exercisesData = exerciseDataStore.exercises
    val postData = planExerciseDataStore.post

    fun createPlanExercise(planId: String, exerciseId: String, repetitions: Int, sets: Int, restBetweenSets: Int, day: String) {
        planExerciseDataStore.createPlanExercise(planId, exerciseId, repetitions, sets, restBetweenSets, day)
    }

    fun getPlanExercises(planId: String): LiveData<List<PlanExercise>> {
        return planExerciseDataStore.getPlanExercises(planId)
    }

    fun getExercises() {
        exerciseDataStore.getExercises()
    }
}
