package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.ContractCategory
import ipvc.gymbuddy.api.models.requests.contractCategory.CreateContractCategoryRequest
import ipvc.gymbuddy.api.services.ContractCategoryService
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.extensions.toAPIModel
import ipvc.gymbuddy.app.extensions.toDatabaseModel
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.database.LocalDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContractCategoryDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: ContractCategoryDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ContractCategoryDataStore(context).also { instance = it }
        }
    }

    var contractCategories = MutableLiveData<AsyncData<List<ContractCategory>>>(AsyncData(listOf()))
    var post = MutableLiveData<AsyncData<CreateContractCategoryRequest>>(AsyncData())
    val delete = MutableLiveData<AsyncData<Unit>>(AsyncData())

    fun getContractCategories() {
        contractCategories.postValue(AsyncData(contractCategories.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            if (NetworkUtils.isOffline(context)) {
                contractCategories.postValue(AsyncData(
                    LocalDatabase.getInstance(context).contractCategory().getAll().map { it.toAPIModel() },
                    AsyncData.Status.SUCCESS
                ))
                return@launch
            }

            when(val response = ContractCategoryService().getContractCategories())  {
                is RequestResult.Success -> {
                    contractCategories.postValue(AsyncData(response.data.contractCategories, AsyncData.Status.SUCCESS))
                    LocalDatabase.getInstance(context).contractCategory().insertAll(response.data.contractCategories.map { it.toDatabaseModel() })
                }
                is RequestResult.Error -> {
                    contractCategories.postValue(AsyncData(contractCategories.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }

    fun createContractCategory(name: String) {
        val entity = CreateContractCategoryRequest(name)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(ContractCategoryService().createContractCategory(entity)) {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }

    fun deleteContractCategory(id: String) {
        delete.postValue(AsyncData(null, AsyncData.Status.LOADING))
        coroutine.launch {
            when(ContractCategoryService().deleteContractCategory(id))  {
                is RequestResult.Success -> {
                    delete.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                    getContractCategories()
                }
                is RequestResult.Error -> delete.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            delete.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}
