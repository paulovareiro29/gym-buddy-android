package ipvc.gymbuddy.app.fragments.trainer.client.metrics

import Modal
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.trainer.metrics.TrainerClientMetricCreateViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TrainerClientMetricsCreateModal : Modal(R.layout.fragment_trainer_client_metrics_create_modal) {

    interface MetricCreationListener {
        fun onMetricCreated()
    }

    private lateinit var viewModel: TrainerClientMetricCreateViewModel
    private var clientId: String? = null

    private var metricCreationListener: MetricCreationListener? = null

    private lateinit var title: TextView
    private lateinit var metricType: AutoCompleteTextView
    private lateinit var value: TextInputLayout
    private lateinit var dateButton: Button
    private lateinit var submitButton: Button
    private lateinit var messageTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TrainerClientMetricCreateViewModel::class.java]
        clientId = arguments?.getString("clientId")

        if (clientId == null) {
            this.dismiss()
            return
        }

        title = view.findViewById(R.id.modal_title)
        metricType = view.findViewById(R.id.metric_type)
        value = view.findViewById(R.id.value)
        dateButton = view.findViewById(R.id.date)
        submitButton = view.findViewById(R.id.submit_button)
        messageTextView = view.findViewById(R.id.message)

        setUpDatePicker(dateButton)
        resetView()
        loadMetricTypes()

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
                    metricCreationListener?.onMetricCreated()
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
        val metricCategory = (metricType.adapter as DropdownAdapter).selected
        val metricValue = value.editText!!.text.toString()
        val metricDate = parseDate(dateButton.text.toString())


        metricType.error = null
        value.error = null
        dateButton.error = null

        if (!Validator.validateRequiredField(metricType, requireContext())) {
            return
        }

        if (!Validator.validateRequiredField(value.editText!!, requireContext())) {
            return
        }

        if (dateButton == null) {
            dateButton.error = getString(R.string.field_is_required)
            return
        }
        viewModel.createMetric(clientId!!, metricCategory!!.id, metricValue, metricDate!!)
    }

    private fun loadMetricTypes() {
        viewModel.getMetricTypes()

        viewModel.metricTypes.observe(viewLifecycleOwner) { response ->
            response.data?.let { allMetrics ->
                val adapter = DropdownAdapter(
                    requireContext(),
                    metricType,
                    allMetrics.map { metricType -> DropdownItem(metricType.id, metricType.name) }
                )
                metricType.setAdapter(adapter)
            }
        }
    }



    private fun resetView() {
        metricType.error = null
        value.error = null
        dateButton.error = null
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

    private fun showMessage(message: String, colorId: Int) {
        messageTextView.text = message
        messageTextView.setTextColor(requireActivity().getColor(colorId))
        messageTextView.visibility = View.VISIBLE
    }

    fun setMetricCreationListener(listener: MetricCreationListener) {
        this.metricCreationListener = listener
    }

    companion object {
        private const val DATE_PICKER_TAG = "DATE_PICKER"
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }
}
