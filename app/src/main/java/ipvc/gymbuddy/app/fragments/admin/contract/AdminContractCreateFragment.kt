package ipvc.gymbuddy.app.fragments.admin.contract

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminUserIndividualContractCreateBinding
import ipvc.gymbuddy.app.viewmodels.admin.contract.AdminContractCreateViewModel
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
        viewModel = ViewModelProvider(this)[AdminContractCreateViewModel::class.java]
        userId = arguments?.getString("userId") ?: return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainerSpinner = binding.trainerSpinner
        categorySpinner = binding.categorySpinner
        startDateButton = binding.startDateButton
        endDateButton = binding.endDateButton

        viewModel.loadTrainers()
        viewModel.loadCategories()

        viewModel.trainers.observe(viewLifecycleOwner) { trainers ->
            val trainerNames = trainers.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, trainerNames)
            trainerSpinner.setAdapter(adapter)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categoryNames)
            categorySpinner.setAdapter(adapter)
        }

        setUpDatePicker(startDateButton)
        setUpDatePicker(endDateButton)

        binding.saveContractButton.setOnClickListener {
            val selectedTrainer = viewModel.trainers.value?.find { it.name == trainerSpinner.text.toString() }
            val selectedCategory = viewModel.categories.value?.find { it.name == categorySpinner.text.toString() }
            val startDate = startDateButton.text.toString()
            val endDate = endDateButton.text.toString()

            if (selectedTrainer != null && selectedCategory != null) {
                viewModel.createContract(userId, selectedTrainer.id, selectedCategory.id, startDate, endDate)
            }
        }
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

}