package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.UserPlan
import ipvc.gymbuddy.api.models.requests.userPlan.CreateUserPlanRequest
import ipvc.gymbuddy.api.services.UserPlanService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.DateUtils
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class UserPlanDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: UserPlanDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: UserPlanDataStore(context).also { instance = it }
        }
    }

    var userPlans = MutableLiveData<AsyncData<List<UserPlan>>>(AsyncData(listOf()))
    var post = MutableLiveData<AsyncData<CreateUserPlanRequest>>(AsyncData())


    fun getUserPlans(userId : String) {
        userPlans.postValue(AsyncData(userPlans.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                userPlans.postValue(AsyncData(
                    LocalDatabase.getInstance(context).userPlan().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = UserPlanService().getUserPlans(userId))  {
                is RequestResult.Success -> {

                    userPlans.postValue(AsyncData(response.data.userPlans, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).userPlan().insertAll(response.data.userPlans.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    userPlans.postValue(AsyncData(userPlans.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }

    fun createUserPlan(userId: String, planId: String, startDate: Date, endDate: Date) {
        val entity = CreateUserPlanRequest(
            planId,
            DateUtils.parseToUTC(startDate),
            DateUtils.parseToUTC(endDate)
        )

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(UserPlanService().createUserPlan(userId, entity)) {
                is RequestResult.Success -> {
                    post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))

                    delay(2500)
                    post.postValue(AsyncData(null, AsyncData.Status.IDLE))
                }
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }
        }
    }
}
