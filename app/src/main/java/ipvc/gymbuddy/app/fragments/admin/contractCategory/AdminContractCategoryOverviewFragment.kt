package ipvc.gymbuddy.app.fragments.admin.contractCategory

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.ContractCategory
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ContractCategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminContractCategoryOverviewBinding
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.viewmodels.admin.contractCategory.AdminContractCategoryOverviewViewModel

class AdminContractCategoryOverviewFragment : BaseFragment<FragmentAdminContractCategoryOverviewBinding>(
    FragmentAdminContractCategoryOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminContractCategoryOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.contract_categories_overview))

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getContractCategories()
        viewModel.contractCategoryData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                val adapter = ContractCategoryAdapter(it.data)
                adapter.setOnDeleteListener { category -> showDeleteConfirmationDialog(category) }
                recyclerView.adapter = adapter
            }
        }

        binding.createContractCategory.setOnClickListener { navController.navigate(R.id.admin_contract_category_create_fragment) }
        binding.searchInput.editText?.addTextChangedListener { text ->
            handleSearch(text.toString())
        }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.contractCategoryData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (recyclerView.adapter as ContractCategoryAdapter).updateDataset(filtered)
    }

    private fun showDeleteConfirmationDialog(contractCategory: ContractCategory) {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.admin_offline_fragment)
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, contractCategory.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                viewModel.deleteContractCategory(contractCategory.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
