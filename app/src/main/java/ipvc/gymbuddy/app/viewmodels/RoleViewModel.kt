package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import ipvc.gymbuddy.api.models.responses.RoleResponse
import ipvc.gymbuddy.app.datastore.RoleDataStore

class RoleViewModel(application: Application) : BaseViewModel(application) {
    private val roleDataStore = RoleDataStore.getInstance(application)
    val roles: LiveData<List<RoleResponse>?> = roleDataStore.roles
    val roleStatus: LiveData<String> = roleDataStore.roleStatus

    fun getRoles() {
        roleDataStore.getRoles()
    }
}