package ipvc.gymbuddy.app.fragments.admin.machine

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.MachineAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminMachinesOverviewBinding
import ipvc.gymbuddy.app.utils.NetworkUtils
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
            when (it.status) {
                AsyncData.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val adapter = MachineAdapter(it.data ?: listOf())
                    adapter.setOnDeleteListener { delete ->
                        showDeleteConfirmationDialog(delete)
                    }
                    recyclerView.adapter = adapter
                }
            }
        }

        binding.createMachine.setOnClickListener { navController.navigate(R.id.admin_machine_create_fragment) }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.machinesData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = MachineAdapter(filtered)
    }

    private fun showDeleteConfirmationDialog(machine: Machine) {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.admin_offline_fragment)
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, machine.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                viewModel.deleteMachine(machine.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
