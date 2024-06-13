package ipvc.gymbuddy.app.viewmodels.trainer.metrics

import android.app.Application
import ipvc.gymbuddy.app.datastore.MetricDataStore
import ipvc.gymbuddy.app.datastore.MetricTypeDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class TrainerClientMetricUpdateViewModel(application: Application) : BaseViewModel(application) {
    private val metricDataStore = MetricDataStore.getInstance(application)
    private val metricTypesDataStore = MetricTypeDataStore.getInstance(application)
    val updateData = metricDataStore.update
    val metricTypes = metricTypesDataStore.metricTypes

    fun updateMetric(id: String, typeId: String, value: String) {
        metricDataStore.updateMetric(id, typeId, value)
    }

    fun getMetric(id: String){
        return metricDataStore.getMetric(id)
    }

    fun getMetrics(userId : String) {
        metricDataStore.getMetrics(userId)
    }

    fun getMetricTypes() {
        metricTypesDataStore.getMetricTypes()
    }
}