package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentClientHomeBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.client.ClientHomeViewModel

class ClientHomeFragment : BaseFragment<FragmentClientHomeBinding>(
    FragmentClientHomeBinding::inflate
) {
    private lateinit var authViewModel: AuthenticationViewModel
    private val viewModel: ClientHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.home), true)

        binding.name.text = authViewModel.user.value?.name ?: "Anonymous"

        loadMetrics()
    }

    private fun loadMetrics() {
        viewModel.getClientMetrics()
        viewModel.clientMetricsData.observe(viewLifecycleOwner) { asyncData ->
            if (asyncData.status == AsyncData.Status.SUCCESS && asyncData.data != null) {
                val metrics = asyncData.data
                binding.trainingPlansCount.text = metrics.number_of_associated_plans.toString()
                binding.metricsCount.text = metrics.number_of_metrics.toString()
            }
        }
    }
}
