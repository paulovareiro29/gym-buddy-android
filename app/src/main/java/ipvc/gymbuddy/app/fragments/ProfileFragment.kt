package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentProfileBinding
import ipvc.gymbuddy.app.utils.StringUtils
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.profile), true)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) return@observe
            binding.apply {
                name.text = user.name
                email.text = user.email
                address.text = user.address
                role.text  = StringUtils.capitalize(user.role.name)
            }
        }

        binding.editButton.setOnClickListener {
            when (viewModel.user.value!!.role.name) {
                "admin" -> navController.navigate(R.id.admin_edit_profile_fragment)
                "trainer" -> navController.navigate(R.id.trainer_edit_profile_fragment)
                "default" -> navController.navigate(R.id.client_edit_profile_fragment)
            }
        }
    }
}
