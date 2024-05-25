package ipvc.gymbuddy.app.viewmodels.admin.category

import android.app.Application
import ipvc.gymbuddy.app.datastore.CategoryDataStore
import ipvc.gymbuddy.app.viewmodels.BaseViewModel

class AdminCategoryOverviewViewModel(application: Application) : BaseViewModel(application) {
    private val categoryDataStore = CategoryDataStore.getInstance(application)
    val categoriesData = categoryDataStore.categories
    fun getCategories() {
        categoryDataStore.getCategories()
    }
}