package ipvc.gymbuddy.app.fragments.admin.machine

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.MachineAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminMachinesOverviewBinding
import ipvc.gymbuddy.app.viewmodels.admin.machine.AdminMachineOverviewViewModel

class AdminMachineOverviewFragment : BaseFragment<FragmentAdminMachinesOverviewBinding>(
    FragmentAdminMachinesOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminMachineOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.machines_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getMachines()
        viewModel.machinesData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = MachineAdapter(it.data)
            }
        }

        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filteredUsers = viewModel.machinesData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = MachineAdapter(filteredUsers)
    }
}
