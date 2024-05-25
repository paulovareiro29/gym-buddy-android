package ipvc.gymbuddy.app.viewmodels.admin.category

import android.app.Application
import ipvc.gymbuddy.app.datastore.CategoryDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminCategoryCreateViewModel(application: Application) : BaseViewModel(application) {
    private val categoryDataStore = CategoryDataStore.getInstance(application)
    val postData = categoryDataStore.post

    fun createCategory(name: String) {
        categoryDataStore.createCategory(name)
    }
}