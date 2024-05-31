package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore

class AuthenticationViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    val user = authenticationDataStore.user
    val loginStatus = authenticationDataStore.loginStatus
    val activateStatus = authenticationDataStore.activateStatus

    fun init() {
        authenticationDataStore.init()
    }

    fun login(email: String, password: String) {
        authenticationDataStore.login(email, password)
    }

    fun logout() {
        authenticationDataStore.logout()
    }

    fun activate(email: String, password: String, code: String) {
        authenticationDataStore.activate(email, password, code)
    }
}