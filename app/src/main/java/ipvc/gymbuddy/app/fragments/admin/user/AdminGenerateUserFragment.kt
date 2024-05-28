package ipvc.gymbuddy.app.fragments.admin.user

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentAdminGenerateUserBinding
import ipvc.gymbuddy.app.utils.StringUtils
import ipvc.gymbuddy.app.viewmodels.admin.user.AdminGenerateUserViewModel

class AdminGenerateUserFragment : BaseFragment<FragmentAdminGenerateUserBinding>(
    FragmentAdminGenerateUserBinding::inflate
) {
    private lateinit var viewModel: AdminGenerateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.generate_new_user))

        resetView()
        loadRoles()
        binding.submit.setOnClickListener { handleGenerateUser() }
        viewModel.registerData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    binding.message.text = getString(R.string.user_created_successfully, it.data!!.register_code)
                    binding.message.setTextColor(requireActivity().getColor(R.color.secondaryDarkColor))
                    binding.message.visibility = View.VISIBLE
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


    private fun loadRoles() {
        viewModel.getRoles()

        viewModel.roles.observe(viewLifecycleOwner) { roles ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,  roles.map { role -> StringUtils.capitalize(role.name) }.toTypedArray())
            binding.role.setAdapter(adapter)
        }
    }

    private fun handleGenerateUser() {
        val name = binding.name.editText ?: return
        val email = binding.email.editText ?: return
        val role = binding.role.text ?: return

        if (!Validator.validateRequiredField(name, requireContext())) return
        if (!Validator.validateRequiredField(email, requireContext())) return
        if (!Validator.validateEmailField(email, requireContext())) return

        viewModel.generateUser(name.text.toString(), email.text.toString(), role.toString())
    }

    private fun resetView() {
        binding.name.error = null
        binding.name.editText?.text = null
        binding.email.error = null
        binding.email.editText?.text = null
        binding.submit.isEnabled = true
        binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }
}
