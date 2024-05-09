package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
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
        viewModel.activateSuccess.observe(requireActivity()) { success ->
            if (success == null) return@observe

            if (success) {
                binding.message.text = getString(R.string.account_activated)
                binding.message.setTextColor(requireActivity().getColor(R.color.secondaryDarkColor))
            } else {
                binding.message.text = getString(R.string.something_went_wrong)
                binding.message.setTextColor(requireActivity().getColor(R.color.error))
            }

            binding.message.visibility = View.VISIBLE
        }

        resetView()
    }

    private fun resetView() {
        viewModel.activateSuccess.postValue(null)
        binding.message.visibility = View.INVISIBLE
    }

    private fun handleRegisterButtonClick() {
        val email = binding.email.editText
        val password = binding.password.editText
        val code = binding.registerCode.editText

        if (email == null || password == null || code == null) return

        if (!Validator.validateRequiredField(email, requireContext()) ||
            !Validator.validateEmailField(email, requireContext())) return

        if (!Validator.validateRequiredField(password, requireContext()) ||
            !Validator.validatePasswordField(password, requireContext())) return

        if (!Validator.validateRequiredField(code, requireContext())) return

        viewModel.activate(email.text.toString(), password.text.toString(), code.text.toString())
    }
}
