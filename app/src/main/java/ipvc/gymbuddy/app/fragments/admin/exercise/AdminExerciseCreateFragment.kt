package ipvc.gymbuddy.app.fragments.admin.exercise

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.AddCategoryAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentAdminExerciseCreateBinding
import ipvc.gymbuddy.app.viewmodels.admin.exercise.AdminExerciseCreateViewModel

class AdminExerciseCreateFragment : BaseFragment<FragmentAdminExerciseCreateBinding>(
    FragmentAdminExerciseCreateBinding::inflate
) {
    private lateinit var viewModel: AdminExerciseCreateViewModel
    private lateinit var categoriesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.create_exercise))

        resetView()
        loadCategories()

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.submit.setOnClickListener { handleSubmit() }
        binding.searchCategoryInput.editText?.addTextChangedListener { handleSearchCategory(it.toString()) }
        viewModel.postData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    binding.message.text = getString(R.string.created_successfully)
                    binding.message.setTextColor(requireActivity().getColor(R.color.secondaryDarkColor))
                    binding.message.visibility = View.VISIBLE
                }
                AsyncData.Status.ERROR -> {
                    binding.message.text = getString(R.string.something_went_wrong)
                    binding.message.setTextColor(requireActivity().getColor(R.color.error))
                    binding.message.visibility = View.VISIBLE
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
            }
        }
    }

    private fun handleSubmit() {
        val name = binding.name.editText ?: return
        val searchCategory = binding.searchCategoryInput.editText ?: return

        if (!Validator.validateRequiredField(name, requireContext())) return

        val categories = (categoriesRecyclerView.adapter as AddCategoryAdapter).selected
        if (categories.size == 0) {
            searchCategory.error = getString(R.string.field_is_required)
            return
        }

        // viewModel.createExercise(name.text.toString(), "null", categories.map { it.id })
    }

    private fun handleSearchCategory(search: String) {
        val filtered = viewModel.categories.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (categoriesRecyclerView.adapter as AddCategoryAdapter).updateDataset(filtered)
    }

    private fun resetView() {
        binding.name.error = null
        binding.name.editText?.text = null
        binding.searchCategoryInput.error = null
        binding.searchCategoryInput.editText?.text = null
        binding.submit.isEnabled = true
        binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }

    private fun loadCategories() {
        viewModel.getCategories()

        viewModel.categories.observe(viewLifecycleOwner) {
            if (it.data != null) {
                categoriesRecyclerView.adapter = AddCategoryAdapter(it.data)
            }
        }
    }

}
