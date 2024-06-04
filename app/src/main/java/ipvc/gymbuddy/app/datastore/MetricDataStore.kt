package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.api.services.MetricService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.launch

class MetricDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: MetricDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MetricDataStore(context).also { instance = it }
        }
    }

    private val metricService = MetricService()

    var metrics = MutableLiveData<AsyncData<List<Metric>>>(AsyncData(listOf()))
    var metric = MutableLiveData<AsyncData<Metric>>(AsyncData())

    fun getMetrics() {
        metrics.postValue(AsyncData(metrics.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            when (val response = metricService.getMetrics()) {
                is RequestResult.Success -> {
                    Log.d("MetricDataStore", "API Response: ${response.data}")
                    val metricList = response.data.metrics
                    metrics.postValue(AsyncData(metricList, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    metrics.postValue(AsyncData(metrics.value?.data, AsyncData.Status.ERROR))
                    Log.e("MetricDataStore", "Error fetching metrics: ${response.message}")
                }
            }
        }
    }

    fun getMetric(id: String) {
        metric.postValue(AsyncData(metric.value?.data, AsyncData.Status.LOADING))
        coroutine.launch {
            when (val response = metricService.getMetric(id)) {
                is RequestResult.Success -> {
                    metric.postValue(AsyncData(response.data.metric, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    metric.postValue(AsyncData(metric.value?.data, AsyncData.Status.ERROR))
                }
            }
        }
    }
}
