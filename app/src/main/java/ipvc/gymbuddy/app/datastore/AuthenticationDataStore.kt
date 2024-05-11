package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.api.models.requests.ActivateRequest
import ipvc.gymbuddy.api.models.requests.LoginRequest
import ipvc.gymbuddy.api.services.AuthenticationService
import kotlinx.coroutines.launch

class AuthenticationDataStore(context: Context) : BaseDataStore(context) {
    @SuppressLint("StaticFieldLeak")
    companion object {
        @Volatile private var instance: AuthenticationDataStore? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AuthenticationDataStore(context).also { instance = it }
            }
    }

    var user = MutableLiveData<User?>()
    var loginStatus = MutableLiveData("idle")
    var activateStatus = MutableLiveData("idle")

    fun login(email: String, password: String) {
        coroutine.launch {
            loginStatus.postValue("loading")
            when (val response = AuthenticationService().login(LoginRequest(email, password))) {
                is RequestResult.Success -> {
                    user.postValue(response.data.user)
                    loginStatus.postValue("success")
                }
                is RequestResult.Error -> {
                    user.postValue(null)
                    loginStatus.postValue("error")
                }
            }
        }
    }

    fun activate(email: String, password: String, code: String) {
        activateStatus.postValue("loading")

        coroutine.launch {
            when (AuthenticationService().activate(ActivateRequest(email, password, code))) {
                is RequestResult.Success -> activateStatus.postValue("success")
                is RequestResult.Error -> activateStatus.postValue("error")
            }
        }
    }
}