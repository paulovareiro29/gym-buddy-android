package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
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
        binding.submitButton.setOnClickListener { handleLoginButtonClick() }
    }

    private fun handleLoginButtonClick() {
        val email = binding.email.editText
        val password = binding.password.editText

        if (email == null || password == null) return

        if (!Validator.validateRequiredField(email, requireContext())) return
        if (!Validator.validateRequiredField(password, requireContext())) return

        viewModel.login(email.text.toString(), password.text.toString())
    }
}
