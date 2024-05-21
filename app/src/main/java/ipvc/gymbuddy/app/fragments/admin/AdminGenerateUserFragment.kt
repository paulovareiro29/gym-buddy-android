package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.SpinnerAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminGenerateUserBinding
import ipvc.gymbuddy.app.utils.StringUtils
import ipvc.gymbuddy.app.viewmodels.RoleViewModel

class AdminGenerateUserFragment : BaseFragment<FragmentAdminGenerateUserBinding>(
    FragmentAdminGenerateUserBinding::inflate
) {
    private lateinit var roleViewModel: RoleViewModel
    private lateinit var rolesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roleViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.generate_new_user))

        loadRoles()
    }

    private fun loadRoles() {
        roleViewModel.getRoles()

        roleViewModel.roles.observe(viewLifecycleOwner) { roles ->
            rolesAdapter.clear()
            roles.forEach { role ->
                rolesAdapter.add(StringUtils.capitalize(role.name))
            }
            rolesAdapter.notifyDataSetChanged()
        }

        rolesAdapter = SpinnerAdapter(requireContext(),mutableListOf())
        binding.roleDropdown.adapter = rolesAdapter
    }
}
