package ipvc.gymbuddy.app.viewmodels.admin.user

import android.app.Application
import ipvc.gymbuddy.app.datastore.ContractDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminUserIndividualViewModel(application: Application) : BaseViewModel(application) {
    private val contractsDataStore = ContractDataStore.getInstance(application)
    val contracts = contractsDataStore.contracts

    fun getContracts(beneficiary: String) {
        contractsDataStore.getContractsByBeneficiary(beneficiary)
    }
}