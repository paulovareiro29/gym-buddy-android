package ipvc.gymbuddy.app.viewmodels.admin.contractCategory

import android.app.Application
import ipvc.gymbuddy.app.datastore.ContractCategoryDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminContractCategoryCreateViewModel(application: Application) : BaseViewModel(application) {
    private val contractCategoryDataStore = ContractCategoryDataStore.getInstance(application)
    val postData = contractCategoryDataStore.post

    fun createContractCategory(name: String) {
        contractCategoryDataStore.createContractCategory(name)
    }
}