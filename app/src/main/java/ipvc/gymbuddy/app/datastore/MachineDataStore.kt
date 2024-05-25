package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.api.models.requests.machine.CreateMachineRequest
import ipvc.gymbuddy.api.services.MachineService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.delay
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
    var post = MutableLiveData<AsyncData<CreateMachineRequest>>(AsyncData())

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

    fun createMachine(name: String, photo: String?, categories: List<String>) {
        val entity = CreateMachineRequest(name, photo, categories)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(MachineService().createMachine(entity))  {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}