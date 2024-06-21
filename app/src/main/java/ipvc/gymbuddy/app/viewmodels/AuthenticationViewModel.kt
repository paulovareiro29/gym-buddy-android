package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore

class AuthenticationViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    val initialized = authenticationDataStore.initialized
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

    fun getPasswordStrength(password: String): Int {
        var score = 0

        if (password.length >= 8) score += 1
        if (password.any { it.isDigit() }) score += 1
        if (password.any { it.isUpperCase() }) score += 1
        if (password.any { it.isLowerCase() }) score += 1
        if (password.any { "!@#$%^&*()_-+=<>?/{}~|".contains(it) }) score += 1

        return score
    }
}