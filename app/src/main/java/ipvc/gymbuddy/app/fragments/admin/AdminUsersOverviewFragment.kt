package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.UserAdapter
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
            if (it.data != null) {
                val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = UserAdapter(it.data)
            }
        }
    }
}
