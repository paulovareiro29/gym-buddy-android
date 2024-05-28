package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.api.models.requests.category.CreateCategoryRequest
import ipvc.gymbuddy.api.services.CategoryService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.delay
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
    var post = MutableLiveData<AsyncData<CreateCategoryRequest>>(AsyncData())

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

    fun createCategory(name: String) {
        val entity = CreateCategoryRequest(name)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(CategoryService().createCategory(entity))  {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}