package ipvc.gymbuddy.app.models

import android.widget.TextView
import com.google.android.material.button.MaterialButton

data class Toolbar(
    val title: TextView?,
    val menuButton: MaterialButton?,
    val backButton: MaterialButton?
)
