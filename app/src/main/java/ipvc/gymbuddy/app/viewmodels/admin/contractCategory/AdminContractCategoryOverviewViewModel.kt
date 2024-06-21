package ipvc.gymbuddy.app.viewmodels.admin.contractCategory

import android.app.Application
import ipvc.gymbuddy.app.datastore.ContractCategoryDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminContractCategoryOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val contractCategoryDataStore = ContractCategoryDataStore.getInstance(application)
    val contractCategoryData = contractCategoryDataStore.contractCategories

    fun getContractCategories(){
        contractCategoryDataStore.getContractCategories()
    }

    fun deleteContractCategory(id: String) {
        contractCategoryDataStore.deleteContractCategory(id)
    }
}