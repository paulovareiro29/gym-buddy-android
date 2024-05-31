package ipvc.gymbuddy.app.viewmodels.trainer.contract

import android.app.Application
import ipvc.gymbuddy.app.datastore.ContractDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerListClientsOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val contractDataStore = ContractDataStore.getInstance(application)
    val contractsData = contractDataStore.contracts

    fun getContracts() {
        contractDataStore.getContracts()
    }

}