package ipvc.gymbuddy.app.viewmodels.client

import android.app.Application
import ipvc.gymbuddy.app.datastore.MetricDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerClientMetricOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val metricDataStore = MetricDataStore.getInstance(application)
    val metricsData = metricDataStore.metrics

    fun getMetrics(userId : String) {
        metricDataStore.getMetrics(userId)
    }
    fun deleteMetric(id: String) {
        metricDataStore.deleteMetric(id)
    }

}