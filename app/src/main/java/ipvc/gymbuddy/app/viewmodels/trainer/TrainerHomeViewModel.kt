package ipvc.gymbuddy.app.viewmodels.trainer

import android.app.Application
import ipvc.gymbuddy.app.datastore.UserDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerHomeViewModel(application: Application) : BaseViewModel(application) {
    private val userDataStore = UserDataStore.getInstance(application)
    val userStatistics = userDataStore.userStatistics

    fun getStatistics() {
        userDataStore.getUserStatistics()
    }
}