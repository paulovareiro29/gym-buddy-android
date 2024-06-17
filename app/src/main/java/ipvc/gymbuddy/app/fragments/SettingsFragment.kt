package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentSettingsBinding
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.view.animation.AccelerateDecelerateInterpolator
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private lateinit var authViewModel: AuthenticationViewModel
    private var isLanguageOptionsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.settings), true)

        binding.changeLanguage.setOnClickListener {
            toggleLanguageOptions()
        }

        binding.englishOption.setOnClickListener { selectLanguage("en") }
        binding.portugueseOption.setOnClickListener { selectLanguage("pt") }
        binding.logout.setOnClickListener { handleLogout() }
    }

    private fun toggleLanguageOptions() {
        if (!isLanguageOptionsVisible) {
            binding.languageOptions.visibility = View.VISIBLE
            rotateArrow(0f, 90f)
        } else {
            binding.languageOptions.visibility = View.GONE
            rotateArrow(90f, 0f)
        }
        isLanguageOptionsVisible = !isLanguageOptionsVisible
    }

    private fun rotateArrow(fromDegrees: Float, toDegrees: Float) {
        val animator = ObjectAnimator.ofFloat(binding.arrowIcon, "rotation", fromDegrees, toDegrees)
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    private fun selectLanguage(language: String) {
        binding.languageOptions.visibility = View.GONE
        if (isLanguageOptionsVisible) {
            rotateArrow(90f, 0f)
            isLanguageOptionsVisible = false
        }

        super.changeLanguage(language)
    }

    private fun handleLogout(){
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.logout))
            setMessage(getString(R.string.logout_message))
            setPositiveButton(getString(R.string.logout) ) { _, _ ->
                authViewModel.logout()
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}