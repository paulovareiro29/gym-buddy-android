package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminGenerateUserBinding
import ipvc.gymbuddy.app.viewmodels.RoleViewModel

class AdminGenerateUserFragment : BaseFragment<FragmentAdminGenerateUserBinding>(
    FragmentAdminGenerateUserBinding::inflate
) {
    private lateinit var roleViewModel: RoleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roleViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.generate_new_user))

        roleViewModel.getRoles()

        roleViewModel.roles.observe(viewLifecycleOwner) { roles ->
            Log.d("roles", roles?.size.toString())
        }

        /*
        if (roleAdapter == null) {
            roleAdapter = ArrayAdapter(requireContext(), R.layout.fragment_admin_generate_user, mutableListOf())
            binding.roleDropdown.setAdapter(roleAdapter)
        }

        roleViewModel.roles.observe(viewLifecycleOwner) { roles ->
            roles?.forEach { roleResponse ->
                roleResponse.roles.forEach { role ->
                    roleAdapter?.add(role.name)
                }
            }
            roleAdapter?.notifyDataSetChanged()
        }) */

        /*if (savedInstanceState == null) {
            viewModel.getRoles()
        } */
    }
}
