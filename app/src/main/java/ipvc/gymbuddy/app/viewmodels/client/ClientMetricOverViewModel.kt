package ipvc.gymbuddy.app.viewmodels.client

import android.app.Application
import android.util.Log
import ipvc.gymbuddy.app.datastore.MetricDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class ClientMetricOverViewModel(application: Application) : BaseViewModel(application) {
    private val metricDataStore = MetricDataStore.getInstance(application)
    val metricsData = metricDataStore.metrics

    fun getMetrics() {
        metricDataStore.getMetrics()
        metricsData.observeForever { data ->
            Log.d("MetricViewModel", "Data received: ${data.status} - Size: ${data.data?.size}")
        }
    }

}