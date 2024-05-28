package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Role
import ipvc.gymbuddy.api.services.RoleService
import kotlinx.coroutines.launch

class RoleDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: RoleDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: RoleDataStore(context).also { instance = it }
        }
    }

    var roles = MutableLiveData<List<Role>>(listOf())
    var roleStatus = MutableLiveData("idle")

    fun getRoles() {
        coroutine.launch {
            roleStatus.postValue("loading")
            when(val response = RoleService().getRoles())  {
                is RequestResult.Success -> {
                    roles.postValue(response.data.roles)
                    roleStatus.postValue("success")
                }
                is RequestResult.Error -> {
                    roles.postValue(listOf())
                    roleStatus.postValue("error")
                }
            }
        }
    }
}