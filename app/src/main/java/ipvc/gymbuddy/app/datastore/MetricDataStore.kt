package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.api.services.MetricService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
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

    fun getMetrics(userId : String) {
        metrics.postValue(AsyncData(metrics.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                metrics.postValue(AsyncData(
                    LocalDatabase.getInstance(context).metrics().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = MetricService().getMetrics(userId))  {
                is RequestResult.Success -> {

                    metrics.postValue(AsyncData(response.data.metrics, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).metrics().insertAll(response.data.metrics.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    metrics.postValue(AsyncData(metrics.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }

                else -> {}
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

                else -> {}
            }
        }
    }
}
