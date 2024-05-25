package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.api.services.MachineService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.launch

class MachineDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: MachineDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MachineDataStore(context).also { instance = it }
        }
    }

    var machines = MutableLiveData<AsyncData<List<Machine>>>(AsyncData(listOf()))

    fun getMachines() {
        machines.postValue(AsyncData(machines.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            when(val response = MachineService().getMachines())  {
                is RequestResult.Success -> {
                    machines.postValue(AsyncData(response.data.machines, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    machines.postValue(AsyncData(machines.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }
}