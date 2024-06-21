package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import ipvc.gymbuddy.app.datastore.AuthenticationDataStore
import ipvc.gymbuddy.app.datastore.ContractDataStore
import ipvc.gymbuddy.app.datastore.UserDataStore

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    private val authenticationDataStore = AuthenticationDataStore.getInstance(application)
    private val userDataStore = UserDataStore.getInstance(application)
    private val contractDataStore = ContractDataStore.getInstance(application)
    val updateData = userDataStore.update
    val contracts = contractDataStore.contracts

    fun updateAvatar(id: String, avatar: String?) {
        userDataStore.updateUser(id, null, null, avatar)
    }

    fun refreshAuthenticated() {
        authenticationDataStore.getAuthenticated(null)
    }

    fun getContracts() {
        if (authenticationDataStore.user.value == null) return
        contractDataStore.getContractsByBeneficiary(authenticationDataStore.user.value!!.id)
    }
}