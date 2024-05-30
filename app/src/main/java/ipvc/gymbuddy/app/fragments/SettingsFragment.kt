package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import android.content.res.Configuration
import java.util.Locale
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentSettingsBinding
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private var isOptionsVisible = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeLanguage.setOnClickListener {
            toggleLanguageOptions()
        }

        binding.englishOption.setOnClickListener { selectLanguage("en") }
        binding.portugueseOption.setOnClickListener { selectLanguage("pt") }
        binding.logout.setOnClickListener { handleLogout() }
    }

    private fun toggleLanguageOptions() {
        if (!isOptionsVisible) {
            binding.languageOptions.visibility = View.VISIBLE
            rotateArrow(0f, 90f)
        } else {
            binding.languageOptions.visibility = View.GONE
            rotateArrow(90f, 0f)
        }
        isOptionsVisible = !isOptionsVisible
    }

    private fun rotateArrow(fromDegrees: Float, toDegrees: Float) {
        val animator = ObjectAnimator.ofFloat(binding.arrowIcon, "rotation", fromDegrees, toDegrees)
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    private fun selectLanguage(language: String) {
        changeLanguage(language)
        binding.languageOptions.visibility = View.GONE
        if (isOptionsVisible) {
            rotateArrow(90f, 0f)
            isOptionsVisible = false
        }
        updateViews()
    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
        //requireActivity().recreate()
    }

    private fun updateViews() {
        binding.changeLanguage.text = getString(R.string.change_language)
        binding.logout.text = getString(R.string.logout)
        //TODO: Remove or verify if needed after recreated() is fixed
    }

    private fun handleLogout(){
    }

}