package ipvc.gymbuddy.app.viewmodels.client

import android.app.Application
import ipvc.gymbuddy.app.datastore.UserPlanDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerClientPlanViewModel(application: Application) : BaseViewModel(application) {
    private val userPlanDataStore = UserPlanDataStore.getInstance(application)
    val userPlans = userPlanDataStore.userPlans

    fun getUserPlans(userId: String) {
        userPlanDataStore.getUserPlans(userId)
    }
}
