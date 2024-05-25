package ipvc.gymbuddy.app.viewmodels.admin.machine

import android.app.Application
import ipvc.gymbuddy.app.datastore.MachineDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminMachineOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val machineDataStore = MachineDataStore.getInstance(application)
    val machinesData = machineDataStore.machines
    fun getMachines() {
        machineDataStore.getMachines()
    }
}