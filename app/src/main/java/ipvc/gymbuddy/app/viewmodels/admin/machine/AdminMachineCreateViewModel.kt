package ipvc.gymbuddy.app.viewmodels.admin.machine

import android.app.Application
import ipvc.gymbuddy.app.datastore.MachineDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminMachineCreateViewModel(application: Application) : BaseViewModel(application) {
    private val machineDataStore = MachineDataStore.getInstance(application)

}