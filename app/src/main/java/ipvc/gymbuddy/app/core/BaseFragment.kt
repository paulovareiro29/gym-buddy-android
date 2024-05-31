package ipvc.gymbuddy.app.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.models.Toolbar
import java.util.Locale

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB
) : Fragment() {

    protected lateinit var navController: NavController

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var _isRootFragment: Boolean = false
    protected lateinit var toolbar: Toolbar

    protected inline fun <reified T : ViewModel> getViewModel(): T = ViewModelProvider(this)[T::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
        activity?.onBackPressedDispatcher?.addCallback { handleBackButton() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleBackButton() {
        if (!_isRootFragment) {
            navController.navigateUp()
        }
    }

    private fun handleOpenSidebar() {
        val drawer = requireActivity().findViewById<DrawerLayout>(R.id.root) ?: return
        drawer.open()
    }

    protected fun loadToolbar(title: String, isRoot: Boolean = false) {
        toolbar = Toolbar(
            view?.findViewById(R.id.toolbar_title),
            view?.findViewById(R.id.toolbar_menu),
            view?.findViewById(R.id.toolbar_back)
        )

        _isRootFragment = isRoot
        toolbar.title?.text = title
        toolbar.menuButton?.setOnClickListener { handleOpenSidebar() }

        if (isRoot) {
            toolbar.backButton?.visibility = View.INVISIBLE
        } else {
            toolbar.backButton?.setOnClickListener { handleBackButton() }
        }
    }

    protected fun changeLanguage(language: String) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(language))
        )
    }
}
