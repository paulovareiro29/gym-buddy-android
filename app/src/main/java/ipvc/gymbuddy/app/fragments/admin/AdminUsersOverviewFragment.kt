package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminUsersOverviewBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class AdminUsersOverviewFragment : BaseFragment<FragmentAdminUsersOverviewBinding>(
    FragmentAdminUsersOverviewBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.users_overview))
    }
}
