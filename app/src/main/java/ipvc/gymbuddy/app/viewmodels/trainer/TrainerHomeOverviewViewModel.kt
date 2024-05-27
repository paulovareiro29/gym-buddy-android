package ipvc.gymbuddy.app.viewmodels.trainer

import android.app.Application
import ipvc.gymbuddy.app.datastore.UserDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerHomeOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val userMetricDataStore = UserDataStore.getInstance(application)
    val userMetricsData = userMetricDataStore.userMetrics

    fun getUserMetrics() {
        userMetricDataStore.getUserMetrics()
    }
}