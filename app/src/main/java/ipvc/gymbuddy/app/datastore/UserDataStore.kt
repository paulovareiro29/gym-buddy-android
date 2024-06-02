package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.api.models.UserStatistic
import ipvc.gymbuddy.api.models.requests.user.UpdateUserRequest
import ipvc.gymbuddy.api.services.UserService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.delay
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
    var userStatistics = MutableLiveData<AsyncData<UserStatistic>>(AsyncData(null))
    var update = MutableLiveData<AsyncData<UpdateUserRequest>>(AsyncData())

    fun getUsers() {
        users.postValue(AsyncData(users.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                users.postValue(AsyncData(
                    LocalDatabase.getInstance(context).user().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
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

    fun getUserStatistics() {
        val user = authenticationDataStore.user.value ?: return
        userStatistics.postValue(AsyncData(userStatistics.value?.data, AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                userStatistics.postValue(AsyncData(
                    LocalDatabase.getInstance(context).user().getStatistics(user.id).toAPIModel(),
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when (val response = UserService().getStatistics(user.id)) {
                is RequestResult.Success -> {
                    val statistic = UserStatistic(
                        user.id,
                        response.data.number_of_contracts,
                        response.data.number_of_created_plans,
                        response.data.number_of_associated_plans,
                        response.data.number_of_metrics,
                    )
                    userStatistics.postValue(AsyncData(statistic, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).user().insertStatistic(statistic.toDatabaseModel())
                }

                is RequestResult.Error -> {
                    userStatistics.postValue(AsyncData(userStatistics.value?.data, AsyncData.Status.ERROR))
                }
            }
        }
    }

    fun updateUser(id: String, name: String?, email: String?) {
        val entity = UpdateUserRequest(name, email)
        update.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(UserService().updateUser(id, entity)) {
                is RequestResult.Success -> update.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> update.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            update.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}