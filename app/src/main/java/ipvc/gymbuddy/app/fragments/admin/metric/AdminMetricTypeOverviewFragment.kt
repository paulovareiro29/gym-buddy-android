package ipvc.gymbuddy.app.fragments.admin.metric

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.AdminCategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminMetricTypeOverviewBinding
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.viewmodels.admin.metric.AdminMetricTypeOverviewViewModel

class AdminMetricTypeOverviewFragment : BaseFragment<FragmentAdminMetricTypeOverviewBinding>(
    FragmentAdminMetricTypeOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminMetricTypeOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.metric_types_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getMetricTypes()
        viewModel.metricTypesData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                val adapter = AdminCategoryAdapter(it.data.map { metricType -> Category(metricType.id, metricType.name) })
                adapter.setOnDeleteListener { metricType -> showDeleteConfirmationDialog(metricType) }
                recyclerView.adapter = adapter
            }
        }

        binding.createMetricType.setOnClickListener { navController.navigate(R.id.admin_metric_type_create_fragment) }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.metricTypesData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (recyclerView.adapter as AdminCategoryAdapter).updateDataset(filtered.map { metricType -> Category(metricType.id, metricType.name) })
    }

    private fun showDeleteConfirmationDialog(metricType: Category) {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.admin_offline_fragment)
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, metricType.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                viewModel.deleteMetricType(metricType.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
