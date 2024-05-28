package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Contract
import ipvc.gymbuddy.api.services.ContractService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.launch

class ContractDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: ContractDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ContractDataStore(context).also { instance = it }
        }
    }

    private val authenticationDataStore = AuthenticationDataStore.getInstance(context)
    var contracts = MutableLiveData<AsyncData<List<Contract>>>(AsyncData(listOf()))


    fun getContracts() {
        val user = authenticationDataStore.user.value
        contracts.postValue(AsyncData(contracts.value?.data, AsyncData.Status.LOADING))
        coroutine.launch {
            if (user != null) {
                when (val response = ContractService().getContract(user.id)) {
                    is RequestResult.Success -> {
                        contracts.postValue(AsyncData(response.data.contracts, AsyncData.Status.SUCCESS))
                    }

                    is RequestResult.Error -> {
                        contracts.postValue(AsyncData(contracts.value?.data, AsyncData.Status.ERROR))
                    }
                }
            }
        }
    }
}