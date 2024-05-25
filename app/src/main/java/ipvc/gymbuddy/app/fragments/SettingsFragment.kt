package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import android.content.res.Configuration
import java.util.Locale
import androidx.appcompat.app.AlertDialog
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeLanguage.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Português")
        val languageCodes = arrayOf("en", "pt")

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.change_language))
            .setItems(languages) { dialog, which ->
                changeLanguage(languageCodes[which])
                updateViews()
                dialog.dismiss()
            }
            .show()
    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
    }

    private fun updateViews() {
        binding.changeLanguage.text = getString(R.string.change_language)
        binding.logout.text = getString(R.string.logout)
    }
}