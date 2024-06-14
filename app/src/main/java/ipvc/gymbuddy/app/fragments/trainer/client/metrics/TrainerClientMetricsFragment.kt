package ipvc.gymbuddy.app.fragments.trainer.client.metrics

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainerMetricAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerClientMetricsBinding
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientMetricOverviewViewModel

class TrainerClientMetricsFragment : BaseFragment<FragmentTrainerClientMetricsBinding>(
    FragmentTrainerClientMetricsBinding::inflate), TrainerClientMetricsCreateModal.MetricCreationListener {

    private lateinit var viewModel: TrainerClientMetricOverviewViewModel
    private lateinit var userId: String

    companion object {
        private const val ARG_USER_ID = "userId"

        fun newInstance(userId: String): TrainerClientMetricsFragment {
            val fragment = TrainerClientMetricsFragment()
            val args = Bundle()
            args.putString(ARG_USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString(ARG_USER_ID) ?: return

        viewModel.getMetrics(userId)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TrainerMetricAdapter(childFragmentManager, listOf())

        binding.createMetric.setOnClickListener { handleCreateMetric() }
        viewModel.metricsData.observe(viewLifecycleOwner) { resource ->
            val metrics = resource.data ?: return@observe
            recyclerView.adapter = TrainerMetricAdapter(childFragmentManager, metrics).apply {
                setOnMetricDeleteListener { metric -> showDeleteConfirmationDialog(metric) }
            }
        }

    }

    override fun onMetricCreated() {
        val userId = arguments?.getString(ARG_USER_ID) ?: return
        viewModel.getMetrics(userId)
    }

    private fun handleCreateMetric() {
        val bundle = Bundle().apply {
            putString("clientId", userId)
        }
        val dialogFragment = TrainerClientMetricsCreateModal().apply {
            arguments = bundle
        }
        dialogFragment.setMetricCreationListener(this@TrainerClientMetricsFragment)
        dialogFragment.show(childFragmentManager, "TrainerTrainingPlanExerciseCreateModal")

    }

    private fun showDeleteConfirmationDialog(metric: Metric) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, metric.type!!.name))
            setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteMetric(metric.id)
                val userId = arguments?.getString(ARG_USER_ID)
                userId?.let {
                    viewModel.getMetrics(it)
                }
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
