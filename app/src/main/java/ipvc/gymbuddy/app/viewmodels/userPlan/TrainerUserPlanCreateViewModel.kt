package ipvc.gymbuddy.app.viewmodels.userPlan

import android.app.Application
import ipvc.gymbuddy.app.datastore.ContractDataStore
import ipvc.gymbuddy.app.datastore.UserPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel
import java.util.Date

class TrainerUserPlanCreateViewModel(application: Application) : BaseViewModel(application) {
    private val userPlanDataStore = UserPlanDataStore.getInstance(application)
    private val contractDataStore = ContractDataStore.getInstance(application)

    val postData = userPlanDataStore.post
    val contractsData = contractDataStore.contracts

    fun createUserPlan(userId: String, planId: String, startDate: Date, endDate: Date) {
        userPlanDataStore.createUserPlan(userId, planId, startDate, endDate)
    }

    fun getContracts() {
        contractDataStore.getContracts()
    }
}
