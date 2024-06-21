package ipvc.gymbuddy.app.fragments.admin.contract

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.google.android.material.datepicker.MaterialDatePicker
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminUserIndividualContractCreateBinding
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.admin.contract.AdminContractCreateViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdminContractCreateFragment : BaseFragment<FragmentAdminUserIndividualContractCreateBinding>(
    FragmentAdminUserIndividualContractCreateBinding::inflate
) {

    private lateinit var viewModel: AdminContractCreateViewModel
    private lateinit var userId: String
    private lateinit var trainerSpinner: AutoCompleteTextView
    private lateinit var categorySpinner: AutoCompleteTextView
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        userId = arguments?.getString("userId") ?: return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.add_contract), false)

        trainerSpinner = binding.trainerSpinner
        categorySpinner = binding.categorySpinner
        startDateButton = binding.startDateButton
        endDateButton = binding.endDateButton

        loadTrainers()
        loadCategories()

        setUpDatePicker(startDateButton)
        setUpDatePicker(endDateButton)

        binding.saveContractButton.setOnClickListener { handleSubmit() }
        viewModel.postData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.saveContractButton.isEnabled = false
                    binding.saveContractButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
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
                    binding.saveContractButton.isEnabled = false
                    binding.saveContractButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
            }
        }
    }

    private fun resetView() {
        binding.trainerSpinner.error = null
        binding.categorySpinner.error = null
        binding.startDateButton.error = null
        binding.endDateButton.error = null
        binding.saveContractButton.isEnabled = true
        binding.saveContractButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }

    private fun handleSubmit() {
        val trainerDropdown = binding.trainerSpinner
        val categoryDropdown = binding.categorySpinner
        val startDate = parseDate(startDateButton.text.toString())
        val endDate = parseDate(endDateButton.text.toString())

        val trainer = (trainerDropdown.adapter as DropdownAdapter).selected
        if (trainer == null) {
            trainerDropdown.error = getString(R.string.field_is_required)
            return
        }

        val category = (categoryDropdown.adapter as DropdownAdapter).selected
        if (category == null) {
            categoryDropdown.error = getString(R.string.field_is_required)
            return
        }

        if (startDate == null) {
            binding.startDateButton.error = getString(R.string.field_is_required)
            return
        }

        if (endDate == null) {
            binding.endDateButton.error = getString(R.string.field_is_required)
            return
        }


        viewModel.createContract(userId, trainer.id, category.id, startDate, endDate)
    }
    private fun setUpDatePicker(button: Button) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selection?.let {
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                button.text = format.format(Date(selection))
            }
        }

        button.setOnClickListener {
            datePicker.show(childFragmentManager, "DATE_PICKER")
        }
    }

    private fun parseDate(dateString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            format.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun loadTrainers() {
        viewModel.loadTrainers()
        viewModel.trainers.observe(viewLifecycleOwner) { trainers ->
            val adapter = DropdownAdapter(requireContext(), binding.trainerSpinner, trainers.map { trainer -> DropdownItem(trainer.id, trainer.name!!) })
            binding.trainerSpinner.setAdapter(adapter)
        }
    }

    private fun loadCategories() {
        viewModel.loadCategories()
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val adapter = DropdownAdapter(requireContext(), binding.categorySpinner, categories.map { category -> DropdownItem(category.id, category.name) })
            binding.categorySpinner.setAdapter(adapter)
        }
    }
}