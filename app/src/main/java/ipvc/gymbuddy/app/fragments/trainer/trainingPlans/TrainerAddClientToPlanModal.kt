import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.userPlan.TrainerUserPlanCreateViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TrainerAddClientToPlanModal : Modal(R.layout.fragment_trainer_add_client_to_plan_modal) {

    private lateinit var viewModel: TrainerUserPlanCreateViewModel
    private var trainingPlanId: String? = null

    private lateinit var clientAutoCompleteTextView: AutoCompleteTextView
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var titleTextView: TextView
    private lateinit var submitButton: Button
    private lateinit var messageTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TrainerUserPlanCreateViewModel::class.java]
        trainingPlanId = arguments?.getString("trainingPlanId")
        Log.d("TrainerAddClientToPlanModal", "Training Plan ID: $trainingPlanId")

        clientAutoCompleteTextView = view.findViewById(R.id.client)
        startDateEditText = view.findViewById(R.id.edit_text_start_date)
        endDateEditText = view.findViewById(R.id.edit_text_end_date)
        titleTextView = view.findViewById(R.id.modal_title)
        submitButton = view.findViewById(R.id.submit_button)
        messageTextView = view.findViewById(R.id.information_message)

        setUpDatePicker(startDateEditText)
        setUpDatePicker(endDateEditText)
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
        val clientDropdown = clientAutoCompleteTextView
        val startDateText = startDateEditText.text.toString()
        val endDateText = endDateEditText.text.toString()

        val client = (clientDropdown.adapter as DropdownAdapter).selected
        if (client == null) {
            showMessage(getString(R.string.field_is_required), R.color.error)
            return
        }

        if (trainingPlanId == null) {
            showMessage(getString(R.string.field_is_required), R.color.error)
            return
        }

        val startDate = parseDate(startDateText)
        val endDate = parseDate(endDateText)

        if (startDate == null || endDate == null) {
            showMessage(getString(R.string.field_is_required), R.color.error)
            startDateEditText.error = getString(R.string.field_is_required)
            endDateEditText.error = getString(R.string.field_is_required)
            return
        }

        val utcStartDate = formatToUtc(startDate)
        val utcEndDate = formatToUtc(endDate)

        Log.d("TrainerAddClientToPlanModal", "Client ID: ${client.id}")
        Log.d("TrainerAddClientToPlanModal", "Training Plan ID: $trainingPlanId")
        Log.d("TrainerAddClientToPlanModal", "Start Date: $utcStartDate")
        Log.d("TrainerAddClientToPlanModal", "End Date: $utcEndDate")

        viewModel.createUserPlan(client.id, trainingPlanId!!, utcStartDate, utcEndDate)
    }

    private fun formatToUtc(date: Date): String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        return utcFormat.format(date)
    }

    private fun resetView() {
        clientAutoCompleteTextView.error = null
        startDateEditText.text = null
        endDateEditText.text = null
        submitButton.isEnabled = true
        submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        messageTextView.visibility = View.INVISIBLE
    }

    private fun setUpDatePicker(editText: EditText) {
        editText.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                editText.setText(format.format(selection))
            }

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
                    data.map { contract ->
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
        private const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val UTC_TIME_ZONE = "UTC"
    }
}
