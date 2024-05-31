package ipvc.gymbuddy.app.viewmodels.client

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ipvc.gymbuddy.app.datastore.UserDataStore

class ClientHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val userDataStore = UserDataStore.getInstance(application)
    val clientMetricsData = userDataStore.userMetrics

    fun getClientMetrics() {
        userDataStore.getUserMetrics()
    }
}
