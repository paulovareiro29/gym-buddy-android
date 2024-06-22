package ipvc.gymbuddy.app.viewmodels.admin.metric

import android.app.Application
import ipvc.gymbuddy.app.datastore.MetricTypeDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminMetricTypeCreateViewModel(application: Application) : BaseViewModel(application) {
    private val metricTypeDataStore = MetricTypeDataStore.getInstance(application)
    val postData = metricTypeDataStore.post

    fun createMetricType(name: String) {
        metricTypeDataStore.createMetricType(name)
    }
}