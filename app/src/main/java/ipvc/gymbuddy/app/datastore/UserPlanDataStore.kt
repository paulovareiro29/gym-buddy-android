package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.requests.userPlan.CreateUserPlanRequest
import ipvc.gymbuddy.api.services.UserPlanService
import ipvc.gymbuddy.app.core.AsyncData
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

    var post = MutableLiveData<AsyncData<CreateUserPlanRequest>>(AsyncData())

    fun createUserPlan(userId: String, planId: String, startDate: Date, endDate: Date) {
        val entity = CreateUserPlanRequest(userId, planId, startDate, endDate)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(UserPlanService().createUserPlan(userId, entity)) {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}
