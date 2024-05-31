package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentSettingsBinding
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private var isLanguageOptionsVisible = false
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
    }

}