package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.SpinnerAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentAdminGenerateUserBinding
import ipvc.gymbuddy.app.utils.StringUtils
import ipvc.gymbuddy.app.viewmodels.GenerateUserViewModel

class AdminGenerateUserFragment : BaseFragment<FragmentAdminGenerateUserBinding>(
    FragmentAdminGenerateUserBinding::inflate
) {
    private lateinit var viewModel: GenerateUserViewModel
    private lateinit var rolesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.generate_new_user))

        loadRoles()
        binding.submit.setOnClickListener { handleGenerateUser() }
    }

    private fun loadRoles() {
        viewModel.getRoles()

        viewModel.roles.observe(viewLifecycleOwner) { roles ->
            rolesAdapter.clear()
            roles.forEach { role ->
                rolesAdapter.add(StringUtils.capitalize(role.name))
            }
            rolesAdapter.notifyDataSetChanged()
        }

        rolesAdapter = SpinnerAdapter(requireContext(),mutableListOf())
        binding.role.adapter = rolesAdapter
    }

    private fun handleGenerateUser() {
        val name = binding.name.editText
        val email = binding.email.editText
        val role = binding.role.selectedItem.toString()

        if (name == null || email == null) return

        if (!Validator.validateRequiredField(name, requireContext())) return
        if (!Validator.validateRequiredField(email, requireContext())) return
        if (!Validator.validateEmailField(email, requireContext())) return

        viewModel.generateUser(name.text.toString(), email.text.toString(), role)
    }
}
