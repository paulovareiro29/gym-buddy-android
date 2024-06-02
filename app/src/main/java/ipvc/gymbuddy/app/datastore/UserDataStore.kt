package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.api.models.responses.user.GetStatisticsResponse
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

    private val authenticationDataStore = AuthenticationDataStore.getInstance(context)
    var users = MutableLiveData<AsyncData<List<User>>>(AsyncData(listOf()))
    var userStatistics = MutableLiveData<AsyncData<GetStatisticsResponse>>(AsyncData(null))

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

    fun getUserStatistics() {
        val user = authenticationDataStore.user.value
        userStatistics.postValue(AsyncData(userStatistics.value?.data, AsyncData.Status.LOADING))
        coroutine.launch {
            if (user != null) {
                when (val response = UserService().getStatistics(user.id)) {
                    is RequestResult.Success -> {
                        userStatistics.postValue(AsyncData(response.data, AsyncData.Status.SUCCESS))
                    }

                    is RequestResult.Error -> {
                        userStatistics.postValue(AsyncData(userStatistics.value?.data, AsyncData.Status.ERROR))
                    }
                }
            }
        }
    }

}