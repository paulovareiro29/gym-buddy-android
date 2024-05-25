package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.api.services.CategoryService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.launch

class CategoryDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: CategoryDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: CategoryDataStore(context).also { instance = it }
        }
    }

    var categories = MutableLiveData<AsyncData<List<Category>>>(AsyncData(listOf()))

    fun getCategories() {
        categories.postValue(AsyncData(categories.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            when(val response = CategoryService().getCategories())  {
                is RequestResult.Success -> {
                    categories.postValue(AsyncData(response.data.categories, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    categories.postValue(AsyncData(categories.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }
}