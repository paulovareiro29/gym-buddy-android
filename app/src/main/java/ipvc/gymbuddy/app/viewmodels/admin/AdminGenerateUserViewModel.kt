package ipvc.gymbuddy.app.viewmodels.admin

import android.app.Application
import androidx.lifecycle.LiveData
import ipvc.gymbuddy.api.models.Role
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore
import ipvc.gymbuddy.app.datastore.RoleDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminGenerateUserViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    private val roleDataStore = RoleDataStore.getInstance(application)
    val registerData = authenticationDataStore.registerData
    val roles: LiveData<List<Role>> = roleDataStore.roles

    fun getRoles() {
        roleDataStore.getRoles()
    }

    fun generateUser(name: String, email: String, rolename: String) {
        val role = roles.value?.find { it.name.lowercase() == rolename.lowercase() } ?: return
        authenticationDataStore.register(name, email, role.id)
    }
}