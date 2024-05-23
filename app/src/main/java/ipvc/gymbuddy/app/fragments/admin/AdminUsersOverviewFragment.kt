package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminUsersOverviewBinding
import ipvc.gymbuddy.app.viewmodels.admin.AdminUsersOverviewViewModel

class AdminUsersOverviewFragment : BaseFragment<FragmentAdminUsersOverviewBinding>(
    FragmentAdminUsersOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminUsersOverviewViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.users_overview))

        viewModel.getUsers()

        viewModel.usersData.observe(viewLifecycleOwner) {
            Log.d("test", it.toString())
        }
    }
}
