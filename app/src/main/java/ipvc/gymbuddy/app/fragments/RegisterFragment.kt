package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentRegisterBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener { handleRegisterButtonClick() }
    }

    private fun handleRegisterButtonClick() {
        val email = binding.email.editText
        val password = binding.password.editText
        val registerCode = binding.registerCode.editText

        if (email == null || password == null || registerCode == null) return

        if (!Validator.validateRequiredField(email, requireContext()) ||
            !Validator.validateEmailField(email, requireContext())) return

        if (!Validator.validateRequiredField(password, requireContext()) ||
            !Validator.validatePasswordField(password, requireContext())) return

        if (!Validator.validateRequiredField(registerCode, requireContext())) return

        // viewModel.login(email.text.toString(), password.text.toString())
    }
}
