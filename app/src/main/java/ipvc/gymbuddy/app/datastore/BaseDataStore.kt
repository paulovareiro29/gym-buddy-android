package ipvc.gymbuddy.app.datastore

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Suppress("unused")
open class BaseDataStore(val context: Context) {
    private val parentJob = Job()
    val coroutine = CoroutineScope(Dispatchers.Main + parentJob)

}