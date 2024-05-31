package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.core.SecureStorage
import ipvc.gymbuddy.api.core.TokenStorage
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.api.models.requests.auth.ActivateRequest
import ipvc.gymbuddy.api.models.requests.auth.LoginRequest
import ipvc.gymbuddy.api.models.requests.auth.RegisterRequest
import ipvc.gymbuddy.api.services.AuthenticationService
import ipvc.gymbuddy.app.core.AsyncData
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

    private var secureStorage = SecureStorage("AUTH_STORAGE", context)
    val USER_KEY = "USER"

    var user = MutableLiveData<User?>()
    var loginStatus = MutableLiveData("idle")
    var activateStatus = MutableLiveData("idle")
    var registerData = MutableLiveData<AsyncData<User?>>(AsyncData())

    fun init() {
        user.postValue(secureStorage.getObject(USER_KEY, User::class.java))
    }

    fun login(email: String, password: String) {
        coroutine.launch {
            loginStatus.postValue("loading")
            when (val response = AuthenticationService().login(LoginRequest(email, password))) {
                is RequestResult.Success -> {
                    TokenStorage.getInstance().setToken(response.data.token)
                    secureStorage.setObject(USER_KEY, response.data.user)
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

    fun register(name: String, email: String, roleId: String?) {
        registerData.postValue(AsyncData(null, AsyncData.Status.LOADING))
        coroutine.launch {
            when (val response = AuthenticationService().register(RegisterRequest(name, email, roleId))) {
                is RequestResult.Success -> {
                    registerData.postValue(AsyncData(response.data.user, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    registerData.postValue(AsyncData(null, AsyncData.Status.ERROR, response.errors))
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