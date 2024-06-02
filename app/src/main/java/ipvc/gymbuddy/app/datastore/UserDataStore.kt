package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.api.models.responses.user.GetAllMetricsResponse
import ipvc.gymbuddy.api.services.UserService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
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
    var userMetrics = MutableLiveData<AsyncData<GetAllMetricsResponse>>(AsyncData(null))

    fun getUsers() {
        users.postValue(AsyncData(users.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (!NetworkUtils.isOnline(context)) {
                users.postValue(AsyncData(LocalDatabase.getInstance(context).user().getAll().map { it.toAPIModel() }, AsyncData.Status.SUCCESS))
                return@launch
            }

            when(val response = UserService().getUsers())  {
                is RequestResult.Success -> {
                    users.postValue(AsyncData(response.data.users, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).user().insertAll(response.data.users.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    users.postValue(AsyncData(users.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }

    fun getUserMetrics() {
        val user = authenticationDataStore.user.value ?: return

        userMetrics.postValue(AsyncData(userMetrics.value?.data, AsyncData.Status.LOADING))
        coroutine.launch {
            when (val response = UserService().getMetrics(user.id)) {
                is RequestResult.Success -> {
                    userMetrics.postValue(AsyncData(response.data, AsyncData.Status.SUCCESS))
                }

                is RequestResult.Error -> {
                    userMetrics.postValue(AsyncData(userMetrics.value?.data, AsyncData.Status.ERROR))
                }
            }
        }
    }

}