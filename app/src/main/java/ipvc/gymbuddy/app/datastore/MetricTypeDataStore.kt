package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.MetricType
import ipvc.gymbuddy.api.models.requests.metricTypes.CreateMetricTypeRequest
import ipvc.gymbuddy.api.services.MetricTypeService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.delay
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
    var post = MutableLiveData<AsyncData<CreateMetricTypeRequest>>(AsyncData())
    val delete = MutableLiveData<AsyncData<Unit>>(AsyncData())

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

    fun createMetricType(name: String) {
        val entity = CreateMetricTypeRequest(name)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(MetricTypeService().createMetricType(entity))  {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun deleteMetricType(id: String) {
        delete.postValue(AsyncData(null, AsyncData.Status.LOADING))
        coroutine.launch {
            when(MetricTypeService().deleteMetricType(id))  {
                is RequestResult.Success -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                    getMetricTypes()
                }
                is RequestResult.Error -> delete.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            delete.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}