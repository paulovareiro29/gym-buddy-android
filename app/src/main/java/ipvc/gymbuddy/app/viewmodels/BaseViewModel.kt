package ipvc.gymbuddy.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ipvc.gymbuddy.api.core.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val parentJob = Job()
    @Suppress("unused")
    val coroutine = CoroutineScope(Dispatchers.Main + parentJob)

    init {
        TokenStorage.getInstance().init(application)
    }
}