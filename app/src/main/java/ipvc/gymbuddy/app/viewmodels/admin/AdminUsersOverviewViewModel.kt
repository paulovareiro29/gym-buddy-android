package ipvc.gymbuddy.app.viewmodels.admin

import android.app.Application
import ipvc.gymbuddy.app.datastore.UserDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminUsersOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val usersDataStore = UserDataStore.getInstance(application)
    val usersData = usersDataStore.users

    fun getUsers() {
        usersDataStore.getUsers()
    }
}