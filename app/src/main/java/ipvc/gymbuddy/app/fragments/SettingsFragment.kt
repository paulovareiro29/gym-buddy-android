package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import android.content.res.Configuration
import java.util.Locale
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeLanguage.setOnClickListener {
            toggleLanguageOptions()
        }

        binding.englishOption.setOnClickListener { selectLanguage(it) }
        binding.portugueseOption.setOnClickListener { selectLanguage(it) }
    }

    private fun toggleLanguageOptions() {
        if (binding.languageOptions.visibility == View.GONE) {
            binding.languageOptions.visibility = View.VISIBLE
            binding.changeLanguage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.globe, 0, R.drawable.baseline_arrow_drop_up_24, 0)
        } else {
            binding.languageOptions.visibility = View.GONE
            binding.changeLanguage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.globe, 0, R.drawable.baseline_arrow_drop_down_24, 0)
        }
    }

    fun selectLanguage(view: View) {
        val languageCode = when (view.id) {
            R.id.english_option -> "en"
            R.id.portuguese_option -> "pt"
            else -> return
        }
        changeLanguage(languageCode)
        binding.languageOptions.visibility = View.GONE
        binding.changeLanguage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.globe, 0, R.drawable.baseline_arrow_drop_down_24, 0)
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
    }

    private fun endOfSession(){
    }

}