package ipvc.gymbuddy.app.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import ipvc.gymbuddy.app.R

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB
) : Fragment() {

    protected lateinit var navController: NavController

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var _isRootFragment: Boolean = false

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

        val toolbarBackButton = view?.findViewById<Button>(R.id.toolbar_back)
        toolbarBackButton?.setOnClickListener { handleBackButton() }

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

    protected fun setTitle(title: String) {
        val toolbar = view?.findViewById<TextView>(R.id.toolbar_title) ?: return
        toolbar.text = title
    }

    protected fun setRootFragment() {
        _isRootFragment = true
    }
}
