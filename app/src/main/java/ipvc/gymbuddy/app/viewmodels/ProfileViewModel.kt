package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore
import ipvc.gymbuddy.app.datastore.UserDataStore

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    private val userDataStore = UserDataStore.getInstance(application)
    val updateData = userDataStore.update

    fun updateAvatar(id: String, avatar: String?) {
        userDataStore.updateUser(id, null, null, avatar)
    }

    fun refreshAuthenticated() {
        authenticationDataStore.getAuthenticated(null)
    }
}