package ipvc.gymbuddy.app.viewmodels.trainer.client.metrics

import android.app.Application
import ipvc.gymbuddy.app.datastore.MetricDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerClientMetricDeleteViewModel(application: Application) : BaseViewModel(application) {
    private val metricPlanDataStore = MetricDataStore.getInstance(application)

    fun deleteMetric(id: String) {
        metricPlanDataStore.deleteMetric(id)
    }
}