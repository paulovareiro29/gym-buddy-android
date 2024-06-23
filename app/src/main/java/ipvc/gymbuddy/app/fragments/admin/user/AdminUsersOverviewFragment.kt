package ipvc.gymbuddy.app.fragments.admin.user

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.UserAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminUsersOverviewBinding
import ipvc.gymbuddy.app.viewmodels.admin.user.AdminUsersOverviewViewModel

class AdminUsersOverviewFragment : BaseFragment<FragmentAdminUsersOverviewBinding>(
    FragmentAdminUsersOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminUsersOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.users_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getUsers()
        viewModel.usersData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    recyclerView.adapter = UserAdapter(it.data ?: listOf())
                }
            }
        }

        binding.generateUser.setOnClickListener { navController.navigate(R.id.admin_generate_user_fragment) }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.usersData.value?.data?.filter {
            it.name?.contains(search, true) == true || it.email.contains(search, true)
        } ?: listOf()
        (recyclerView.adapter as UserAdapter).updateDataset(filtered)
    }
}
