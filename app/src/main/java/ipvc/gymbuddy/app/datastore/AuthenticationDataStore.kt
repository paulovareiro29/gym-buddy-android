package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.models.User
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

    fun login(email: String, password: String) {
        coroutine.launch {
            val response = AuthenticationService().login(LoginRequest(email, password))

            if (response != null) {
                user.postValue(response.user)
            }
        }
    }
}