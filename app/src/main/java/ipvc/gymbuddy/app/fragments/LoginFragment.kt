package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import ipvc.gymbuddy.app.databinding.FragmentLoginBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener { handleLoginButtonClick() }
    }

    private fun handleLoginButtonClick() {
        viewModel.login("gymbuddy@ipvc.pt", "gymbuddy")
    }
}
