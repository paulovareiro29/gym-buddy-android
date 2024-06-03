package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore
import ipvc.gymbuddy.app.datastore.UserDataStore

class EditProfileViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    private val userDataStore = UserDataStore.getInstance(application)
    val updateData = userDataStore.update

    fun updateUser(id: String, name: String?, address: String?) {
        userDataStore.updateUser(id, name, address)
    }

    fun refreshAuthenticated() {
        authenticationDataStore.getAuthenticated(null)
    }
}