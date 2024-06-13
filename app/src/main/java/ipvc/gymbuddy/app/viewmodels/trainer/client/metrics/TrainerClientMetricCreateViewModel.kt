package ipvc.gymbuddy.app.viewmodels.trainer.client.metrics

import android.app.Application
import ipvc.gymbuddy.app.datastore.MetricDataStore
import ipvc.gymbuddy.app.datastore.MetricTypeDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel
import java.util.Date

class TrainerClientMetricCreateViewModel(application: Application) : BaseViewModel(application) {
    private val metricDataStore = MetricDataStore.getInstance(application)
    private val metricTypesDataStore = MetricTypeDataStore.getInstance(application)
    val postData = metricDataStore.post
    val metricTypes = metricTypesDataStore.metricTypes

    fun createMetric(userId: String, typeId: String, value: String, date: Date) {
        metricDataStore.createMetric(userId, typeId, value, date)
    }

    fun getMetrics(userId: String) {
        metricDataStore.getMetrics(userId)
    }

    fun getMetricTypes() {
        metricTypesDataStore.getMetricTypes()
    }
}