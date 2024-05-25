package ipvc.gymbuddy.app.viewmodels.admin.machine

import android.app.Application
import ipvc.gymbuddy.app.datastore.CategoryDataStore
import ipvc.gymbuddy.app.datastore.MachineDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminMachineCreateViewModel(application: Application) : BaseViewModel(application) {
    private val machineDataStore = MachineDataStore.getInstance(application)
    private val categoriesDatastore = CategoryDataStore.getInstance(application)

    val postData = machineDataStore.post
    val categories = categoriesDatastore.categories

    fun createMachine(name: String, photo: String?, categories: List<String>) {
        machineDataStore.createMachine(name, photo, categories)
    }

    fun getCategories() {
        categoriesDatastore.getCategories()
    }
}