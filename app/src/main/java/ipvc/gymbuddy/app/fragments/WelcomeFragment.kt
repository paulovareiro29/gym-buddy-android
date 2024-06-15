package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentWelcomeBinding
import ipvc.gymbuddy.app.utils.NetworkUtils

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener { handleLoginClick() }

        binding.registerButton.setOnClickListener { handleRegisterClick() }
    }

    private fun handleLoginClick() {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.welcome_offline_fragment)
            return
        }
        navController.navigate(R.id.action_welcomeFragment_to_loginFragment)
    }

    private fun handleRegisterClick() {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.welcome_offline_fragment)
            return
        }
        navController.navigate(R.id.action_welcomeFragment_to_registerFragment)
    }
}
