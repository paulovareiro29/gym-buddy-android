package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminGenerateUserBinding
import ipvc.gymbuddy.app.viewmodels.RoleViewModel

class AdminGenerateUserFragment : BaseFragment<FragmentAdminGenerateUserBinding>(
    FragmentAdminGenerateUserBinding::inflate
) {
    private lateinit var viewModel: RoleViewModel
    private var roleAdapter: ArrayAdapter<String>? = null // Use nullable type

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        viewModel = getViewModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (roleAdapter == null) {
            roleAdapter = ArrayAdapter(requireContext(), R.layout.fragment_admin_generate_user, mutableListOf())
            binding.roleDropdown.setAdapter(roleAdapter)
        }

        viewModel.roles.observe(viewLifecycleOwner, Observer { roleResponses ->
            roleResponses?.forEach { roleResponse ->
                roleResponse.roles.forEach { role ->
                    roleAdapter?.add(role.name)
                }
            }
            roleAdapter?.notifyDataSetChanged()
        })

        if (savedInstanceState == null) {
            viewModel.getRoles()
        }
    }
}
