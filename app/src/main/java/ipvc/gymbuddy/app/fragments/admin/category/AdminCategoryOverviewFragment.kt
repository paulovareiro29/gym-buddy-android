package ipvc.gymbuddy.app.fragments.admin.category

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
import ipvc.gymbuddy.app.databinding.FragmentAdminCategoryOverviewBinding
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.viewmodels.admin.category.AdminCategoryOverviewViewModel

class AdminCategoryOverviewFragment : BaseFragment<FragmentAdminCategoryOverviewBinding>(
    FragmentAdminCategoryOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminCategoryOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.categories_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getCategories()
        viewModel.categoriesData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                val adapter = AdminCategoryAdapter(it.data)
                adapter.setOnDeleteListener { category -> showDeleteConfirmationDialog(category) }
                recyclerView.adapter = adapter
            }
        }

        binding.createCategory.setOnClickListener { navController.navigate(R.id.admin_category_create_fragment) }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.categoriesData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (recyclerView.adapter as AdminCategoryAdapter).updateDataset(filtered)
    }

    private fun showDeleteConfirmationDialog(category: Category) {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.admin_offline_fragment)
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, category.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                viewModel.deleteCategory(category.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
