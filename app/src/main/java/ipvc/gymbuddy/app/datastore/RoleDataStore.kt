package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Role
import ipvc.gymbuddy.api.services.RoleService
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
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
        roleStatus.postValue("loading")
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                roles.postValue(LocalDatabase.getInstance(context).role().getAll().map { it.toAPIModel() })
                roleStatus.postValue("success")
                return@launch
            }

            when(val response = RoleService().getRoles())  {
                is RequestResult.Success -> {
                    roles.postValue(response.data.roles)
                    roleStatus.postValue("success")
                    LocalDatabase.getInstance(context).role().insertAll(response.data.roles.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    roles.postValue(listOf())
                    roleStatus.postValue("error")
                }
            }
        }
    }
}