package ipvc.gymbuddy.app.fragments.admin.category

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.CategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminCategoryOverviewBinding
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
                recyclerView.adapter = CategoryAdapter(it.data)
            }
        }

        binding.createCategory.setOnClickListener { navController.navigate(R.id.admin_category_create_fragment) }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.categoriesData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (recyclerView.adapter as CategoryAdapter).updateDataset(filtered)
    }
}
