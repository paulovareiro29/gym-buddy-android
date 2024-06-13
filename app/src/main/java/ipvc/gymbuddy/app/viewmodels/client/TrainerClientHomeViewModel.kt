package ipvc.gymbuddy.app.viewmodels.client

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ipvc.gymbuddy.app.datastore.UserDataStore

class TrainerClientHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val userDataStore = UserDataStore.getInstance(application)
    val clientStatisticsData = userDataStore.userStatistics

    fun getStatistics() {
        userDataStore.getUserStatistics()
    }
}
