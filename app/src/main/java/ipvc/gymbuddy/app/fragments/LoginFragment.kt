package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
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
        viewModel.loginStatus.observe(requireActivity()) {
            resetView()

            when (it) {
                "loading" -> {
                    binding.submitButton.isEnabled = false
                    binding.submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                "error" -> {
                    binding.message.text = getString(R.string.wrong_credentials)
                    binding.message.setTextColor(requireActivity().getColor(R.color.error))
                    binding.message.visibility = View.VISIBLE
                }
            }
        }

        viewModel.loginStatus.postValue("idle")
    }

    private fun resetView() {
        binding.email.error = null
        binding.password.error = null
        binding.submitButton.isEnabled = true
        binding.submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
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
