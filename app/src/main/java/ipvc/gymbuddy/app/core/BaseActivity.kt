package ipvc.gymbuddy.app.core

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ipvc.gymbuddy.app.R
import java.util.Locale

abstract class BaseActivity(
    @LayoutRes protected val layoutResId: Int,
    @IdRes protected val navResId: Int,
) : AppCompatActivity() {
    protected lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            SystemBarStyle.dark(getColor(R.color.primaryColor)),
            SystemBarStyle.dark(getColor(R.color.black))
        )
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        LanguageStorage.getInstance().init(this)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(LanguageStorage.getInstance().getCurrentLanguage() ?: "en"))
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navController = (supportFragmentManager.findFragmentById(navResId) as NavHostFragment).navController
    }

    protected inline fun <reified T : ViewModel> getViewModel(): T = ViewModelProvider(this)[T::class.java]

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}