package ipvc.gymbuddy.app.fragments.trainer.client.metrics

import Modal
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.trainer.metrics.TrainerClientMetricUpdateViewModel

class TrainerClientMetricsUpdateModal : Modal(R.layout.fragment_trainer_client_metrics_update_modal) {

    private lateinit var viewModel: TrainerClientMetricUpdateViewModel
    private var metric: Metric? = null

    private lateinit var title: TextView
    private lateinit var metricType: AutoCompleteTextView
    private lateinit var value: TextInputLayout
    private lateinit var submitButton: Button
    private lateinit var messageTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TrainerClientMetricUpdateViewModel::class.java]
        metric = Gson().fromJson(arguments?.getString("metric"), Metric::class.java)
        val titleText = arguments?.getString("title") ?: ""

        if(metric == null){
            this.dismiss()
            return
        }

        title = view.findViewById(R.id.modal_title)
        metricType = view.findViewById(R.id.metric_type)
        value = view.findViewById(R.id.value)
        submitButton = view.findViewById(R.id.submit_button)
        messageTextView = view.findViewById(R.id.message)

        title.text = titleText
        metricType.setText(metric!!.type!!.name)
        value.editText?.setText(metric!!.value)


        resetView()
        loadMetricTypes()

        submitButton.setOnClickListener { handleSubmit() }

        viewModel.updateData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    submitButton.isEnabled = false
                    submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    showMessage(getString(R.string.updated_successfully), R.color.secondaryDarkColor)
                    viewModel.getMetrics(metric!!.user.id)
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

        metricType.error = null
        value.error = null

        if (!Validator.validateRequiredField(metricType, requireContext())) {
            return
        }

        if (!Validator.validateRequiredField(value.editText!!, requireContext())) {
            return
        }

        val categoryId = metricCategory?.id ?: metric?.type?.id ?: ""

        viewModel.updateMetric(metric!!.id, categoryId, metricValue)
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
        submitButton.isEnabled = true
        submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        messageTextView.visibility = View.INVISIBLE
    }

    private fun showMessage(message: String, colorId: Int) {
        messageTextView.text = message
        messageTextView.setTextColor(requireActivity().getColor(colorId))
        messageTextView.visibility = View.VISIBLE
    }
}
