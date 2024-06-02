package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentEditProfileBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.EditProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate
) {
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var viewModel: EditProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = getViewModel()
        viewModel = getViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.edit_profile))

        authViewModel.user.observe(viewLifecycleOwner) {
            binding.name.editText?.setText(it?.name)
            binding.address.editText?.setText(it?.address)
        }

        binding.submit.setOnClickListener { handleSubmit() }
        viewModel.updateData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    binding.message.text = getString(R.string.updated_successfully)
                    binding.message.setTextColor(requireActivity().getColor(R.color.secondaryDarkColor))
                    binding.message.visibility = View.VISIBLE
                    viewModel.refreshAuthenticated()

                    coroutine.launch {
                        delay(2500)
                        resetView()
                    }
                }
                AsyncData.Status.ERROR -> {
                    binding.message.text = getString(R.string.something_went_wrong)
                    binding.message.setTextColor(requireActivity().getColor(R.color.error))
                    binding.message.visibility = View.VISIBLE
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
            }
        }
    }

    private fun handleSubmit() {
        val name = binding.name.editText ?: return
        val address = binding.address.editText ?: return

        if (!Validator.validateRequiredField(name, requireContext())) return

        viewModel.updateUser(
            authViewModel.user.value!!.id,
            name.text.toString(),
            address.text.toString()
        )
    }

    private fun resetView() {
        binding.name.error = null
        binding.name.editText?.setText(authViewModel.user.value!!.name)
        binding.address.error = null
        binding.address.editText?.setText(authViewModel.user.value!!.address)
        binding.submit.isEnabled = true
        binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }
}
