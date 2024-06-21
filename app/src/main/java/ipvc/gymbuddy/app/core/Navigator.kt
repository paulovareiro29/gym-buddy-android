package ipvc.gymbuddy.app.core

import android.content.Context
import android.content.Intent

class Navigator {
    companion object {
        fun resetNavigationTo(activity: Class<out Context>, context: Context) {
            val intent = Intent(context, activity).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }
}