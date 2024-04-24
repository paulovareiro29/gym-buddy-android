package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore

class AuthenticationViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    val user = authenticationDataStore.user

    fun login(email: String, password: String) {
        authenticationDataStore.login(email, password)
    }
}