package ipvc.gymbuddy.app.viewmodels.admin.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.models.ContractCategory
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.app.datastore.ContractDataStore
import ipvc.gymbuddy.app.datastore.UserDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminContractCreateViewModel(application: Application) : BaseViewModel(application) {

    private val userDataStore = UserDataStore.getInstance(application)
    private val contractDataStore = ContractDataStore.getInstance(application)

    val trainers = MutableLiveData<List<User>>()
    val categories = MutableLiveData<List<ContractCategory>>()

    fun loadTrainers() {
        userDataStore.getUsers()
        userDataStore.users.observeForever { asyncData ->
            val data = asyncData.data ?: emptyList()
            val roleTrainer = "trainer"
            trainers.value = data.filter { user -> user.role.name.equals(roleTrainer, ignoreCase = true) }
        }
    }

    fun loadCategories() {
        contractDataStore.getCategories()
        contractDataStore.categories.observeForever { asyncData ->
            categories.value = asyncData.data ?: emptyList()
        }
    }

    fun createContract(beneficiaryId: String, trainerId: String, categoryId: String, startDate: String, endDate: String) {
        contractDataStore.createContract(beneficiaryId, trainerId, categoryId, startDate, endDate)
    }
}
