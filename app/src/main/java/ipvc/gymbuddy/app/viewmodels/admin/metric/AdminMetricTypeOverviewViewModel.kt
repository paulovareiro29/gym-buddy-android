package ipvc.gymbuddy.app.viewmodels.admin.metric

import android.app.Application
import ipvc.gymbuddy.app.datastore.MetricTypeDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminMetricTypeOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val metricTypeDataStore = MetricTypeDataStore.getInstance(application)
    val metricTypesData = metricTypeDataStore.metricTypes
    fun getMetricTypes() {
        metricTypeDataStore.getMetricTypes()
    }

    fun deleteMetricType(id: String) {
        metricTypeDataStore.deleteMetricType(id)
    }
}