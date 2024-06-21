package ipvc.gymbuddy.app.viewmodels.admin.exercise

import android.app.Application
import ipvc.gymbuddy.app.datastore.CategoryDataStore
import ipvc.gymbuddy.app.datastore.ExerciseDataStore
import ipvc.gymbuddy.app.datastore.MachineDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminExerciseCreateViewModel(application: Application) : BaseViewModel(application) {
    private val exerciseDataStore = ExerciseDataStore.getInstance(application)
    private val machineDatastore = MachineDataStore.getInstance(application)
    private val categoryDatastore = CategoryDataStore.getInstance(application)

    val postData = exerciseDataStore.post
    val machines = machineDatastore.machines
    val categories = categoryDatastore.categories

    fun createExercise(name: String, photo: String?, machineId: String, categories: List<String>) {
        exerciseDataStore.createExercise(name, photo, machineId, categories)
    }

    fun getCategories() {
        categoryDatastore.getCategories()
    }

    fun getMachines() {
        machineDatastore.getMachines()
    }
}