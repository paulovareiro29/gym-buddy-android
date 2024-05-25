package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.api.services.UserService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.launch

class UserDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: UserDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: UserDataStore(context).also { instance = it }
        }
    }

    var users = MutableLiveData<AsyncData<List<User>>>(AsyncData(listOf()))

    fun getUsers() {
        users.postValue(AsyncData(users.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            when(val response = UserService().getUsers())  {
                is RequestResult.Success -> {
                    users.postValue(AsyncData(response.data.users, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    users.postValue(AsyncData(users.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }
}