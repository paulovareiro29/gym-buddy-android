package ipvc.gymbuddy.app.viewmodels.admin.machine

import android.app.Application
import ipvc.gymbuddy.app.datastore.CategoryDataStore
import ipvc.gymbuddy.app.datastore.MachineDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminMachineUpdateViewModel(application: Application) : BaseViewModel(application) {
    private val machineDataStore = MachineDataStore.getInstance(application)
    private val categoriesDatastore = CategoryDataStore.getInstance(application)

    val updateData = machineDataStore.update
    val categories = categoriesDatastore.categories

    fun updateMachine(id: String, name: String, photo: String?, categories: List<String>) {
        machineDataStore.updateMachine(id, name, photo, categories)
    }

    fun getCategories() {
        categoriesDatastore.getCategories()
    }
}