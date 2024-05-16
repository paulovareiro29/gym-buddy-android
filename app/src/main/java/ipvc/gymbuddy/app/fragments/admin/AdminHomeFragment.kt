package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminHomeBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class AdminHomeFragment : BaseFragment<FragmentAdminHomeBinding>(
    FragmentAdminHomeBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text = viewModel.user.value!!.name
        binding.generateUser.setOnClickListener { handleGenerateNewUserClick() }

    }

    private fun handleGenerateNewUserClick() {
        navController.navigate(R.id.action_admin_home_fragment_to_adminGenerateUserFragment)
    }
}
