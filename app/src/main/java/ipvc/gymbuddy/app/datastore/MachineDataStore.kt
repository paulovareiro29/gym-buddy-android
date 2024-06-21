package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.api.models.requests.machine.CreateMachineRequest
import ipvc.gymbuddy.api.models.requests.machine.UpdateMachineRequest
import ipvc.gymbuddy.api.services.MachineService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
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
    var update = MutableLiveData<AsyncData<UpdateMachineRequest>>(AsyncData())
    val delete = MutableLiveData<AsyncData<Unit>>(AsyncData())
    
    fun getMachines() {
        machines.postValue(AsyncData(machines.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                machines.postValue(AsyncData(
                    LocalDatabase.getInstance(context).machine().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = MachineService().getMachines())  {
                is RequestResult.Success -> {
                    machines.postValue(AsyncData(response.data.machines, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).machine().insertAll(response.data.machines.map { it.toDatabaseModel() })
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

    fun updateMachine(id: String, name: String, photo: String?, categories: List<String>) {
        val entity = UpdateMachineRequest(name, photo, categories)

        update.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(MachineService().updateMachine(id, entity))  {
                is RequestResult.Success -> update.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> update.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            update.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun deleteMachine(id: String) {
        delete.postValue(AsyncData(null, AsyncData.Status.LOADING))
        coroutine.launch {
            when (MachineService().deleteMachine(id)) {
                is RequestResult.Success -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                    getMachines()
                }
                is RequestResult.Error -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.ERROR))
                }
            }
            delay(2500)
            delete.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}