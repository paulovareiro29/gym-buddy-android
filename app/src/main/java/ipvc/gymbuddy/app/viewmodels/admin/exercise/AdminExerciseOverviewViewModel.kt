package ipvc.gymbuddy.app.viewmodels.admin.exercise

import android.app.Application
import ipvc.gymbuddy.app.datastore.ExerciseDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminExerciseOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val exerciseDataStore = ExerciseDataStore.getInstance(application)
    val exercisesData = exerciseDataStore.exercises
    fun getExercises() {
        exerciseDataStore.getExercises()
    }

    fun deleteExercise(id: String){
        exerciseDataStore.deleteExercise(id)
    }
}