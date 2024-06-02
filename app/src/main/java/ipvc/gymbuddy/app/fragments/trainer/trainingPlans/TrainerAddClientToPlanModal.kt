import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.userPlan.TrainerUserPlanCreateViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrainerAddClientToPlanModal : Modal(R.layout.fragment_trainer_add_client_to_plan_modal) {

    private lateinit var viewModel: TrainerUserPlanCreateViewModel
    private var trainingPlan: TrainingPlan? = null

    private lateinit var clientAutoCompleteTextView: AutoCompleteTextView
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var submitButton: Button
    private lateinit var messageTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TrainerUserPlanCreateViewModel::class.java]
        trainingPlan = Gson().fromJson(arguments?.getString("trainingPlan"), TrainingPlan::class.java)

        if (trainingPlan == null) {
            this.dismiss()
            return
        }

        clientAutoCompleteTextView = view.findViewById(R.id.client)
        startDateButton = view.findViewById(R.id.start_date)
        endDateButton = view.findViewById(R.id.end_date)
        submitButton = view.findViewById(R.id.submit_button)
        messageTextView = view.findViewById(R.id.message)

        setUpDatePicker(startDateButton)
        setUpDatePicker(endDateButton)
        resetView()
        loadClients()

        submitButton.setOnClickListener { handleSubmit() }

        viewModel.postData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    submitButton.isEnabled = false
                    submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    showMessage(getString(R.string.created_successfully), R.color.secondaryDarkColor)
                }
                AsyncData.Status.ERROR -> {
                    showMessage(getString(R.string.something_went_wrong), R.color.error)
                    submitButton.isEnabled = true
                    submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
                }
            }
        }
    }

    private fun handleSubmit() {
        val client = (clientAutoCompleteTextView.adapter as DropdownAdapter).selected
        val startDate = parseDate(startDateButton.text.toString())
        val endDate = parseDate(endDateButton.text.toString())

        clientAutoCompleteTextView.error = null
        startDateButton.error = null
        endDateButton.error = null

        if (client == null) {
            clientAutoCompleteTextView.error = getString(R.string.field_is_required)
            return
        }

        if (startDate == null) {
            startDateButton.error = getString(R.string.field_is_required)
            return
        }

        if (endDate == null) {
            endDateButton.error = getString(R.string.field_is_required)
            return
        }

        Log.d("TrainerAddClientToPlanModal", "Client ID: ${client.id}")
        Log.d("TrainerAddClientToPlanModal", "Training Plan ID: ${trainingPlan!!.id}")
        Log.d("TrainerAddClientToPlanModal", "Start Date: $startDate")
        Log.d("TrainerAddClientToPlanModal", "End Date: $endDate")

        viewModel.createUserPlan(client.id, trainingPlan!!.id, startDate, endDate)
    }

    private fun resetView() {
        clientAutoCompleteTextView.error = null
        startDateButton.text = getString(R.string.start_date)
        startDateButton.error = null
        endDateButton.text = getString(R.string.end_date)
        endDateButton.error = null
        submitButton.isEnabled = true
        submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        messageTextView.visibility = View.INVISIBLE
    }

    private fun setUpDatePicker(button: Button) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            button.text = format.format(selection)
        }

        button.setOnClickListener {
            datePicker.show(childFragmentManager, DATE_PICKER_TAG)
        }
    }

    private fun parseDate(dateString: String): Date? {
        val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return try {
            format.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun loadClients() {
        viewModel.getContracts()

        viewModel.contractsData.observe(viewLifecycleOwner) { contracts ->
            contracts.data?.let { data ->
                val adapter = DropdownAdapter(
                    requireContext(),
                    clientAutoCompleteTextView,
                    data
                        .filter { contract ->
                            trainingPlan?.clients?.find { user -> user.id == contract.beneficiary.id } == null
                        }
                        .map { contract ->
                        DropdownItem(contract.beneficiary.id, contract.beneficiary.name)
                    }
                )
                clientAutoCompleteTextView.setAdapter(adapter)
            }
        }
    }

    private fun showMessage(message: String, colorId: Int) {
        messageTextView.text = message
        messageTextView.setTextColor(requireActivity().getColor(colorId))
        messageTextView.visibility = View.VISIBLE
    }

    companion object {
        private const val DATE_PICKER_TAG = "DATE_PICKER"
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }
}
