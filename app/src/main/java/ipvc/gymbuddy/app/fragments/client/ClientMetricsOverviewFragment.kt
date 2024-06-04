package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.MetricAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentClientMetricsBinding
import ipvc.gymbuddy.app.viewmodels.client.ClientMetricOverViewModel

class ClientMetricsOverviewFragment : BaseFragment<FragmentClientMetricsBinding>(
    FragmentClientMetricsBinding::inflate
) {
    private lateinit var viewModel: ClientMetricOverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.metrics_overview))

        val recyclerView = binding.recyclerViewMetrics
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MetricAdapter(listOf())

        viewModel.getMetrics()
        viewModel.metricsData.observe(viewLifecycleOwner) { asyncData ->
            Log.d("MetricsFragment", "Observing data: ${asyncData.status}")
            if (asyncData.status == AsyncData.Status.SUCCESS && asyncData.data != null) {
                Log.d("MetricsFragment", "Data size: ${asyncData.data.size}")
                (recyclerView.adapter as MetricAdapter).updateDataset(asyncData.data)
            } else if (asyncData.status == AsyncData.Status.ERROR) {
                Log.d("MetricsFragment", "Error loading data")
            }
        }
    }
}
