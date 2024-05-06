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
    var activateSuccess = MutableLiveData<Boolean?>()

    fun login(email: String, password: String) {
        coroutine.launch {
            when (val response = AuthenticationService().login(LoginRequest(email, password))) {
                is RequestResult.Success -> user.postValue(response.data.user)
                is RequestResult.Error -> user.postValue(null)
            }
        }
    }

    fun activate(email: String, password: String, code: String) {
        activateSuccess.postValue(null)

        coroutine.launch {
            when (AuthenticationService().activate(ActivateRequest(email, password, code))) {
                is RequestResult.Success -> activateSuccess.postValue(true)
                is RequestResult.Error -> activateSuccess.postValue(false)
            }
        }
    }
}