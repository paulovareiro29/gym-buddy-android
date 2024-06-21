package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.MetricType
import ipvc.gymbuddy.api.services.MetricTypeService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.launch

class MetricTypeDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: MetricTypeDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MetricTypeDataStore(context).also { instance = it }
        }
    }
    var metricTypes = MutableLiveData<AsyncData<List<MetricType>>>(AsyncData(listOf()))

    fun getMetricTypes() {
        metricTypes.postValue(AsyncData(metricTypes.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                metricTypes.postValue(AsyncData(
                    LocalDatabase.getInstance(context).metricTypes().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = MetricTypeService().getMetricTypes())  {
                is RequestResult.Success -> {

                    metricTypes.postValue(AsyncData(response.data.metricTypes, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).metricTypes().insertAll(response.data.metricTypes.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    metricTypes.postValue(AsyncData(metricTypes.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }

                else -> {}
            }
        }
    }
}