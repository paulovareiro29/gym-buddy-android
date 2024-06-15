package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentClientHomeBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientHomeViewModel

class ClientHomeFragment : BaseFragment<FragmentClientHomeBinding>(
    FragmentClientHomeBinding::inflate
) {
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var viewModel: TrainerClientHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = getViewModel()
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.home), true)

        loadMetrics()

        binding.myMetricsButton.setOnClickListener { navController.navigate(R.id.client_metrics_overview_fragment) }
        binding.trainingPlansButton.setOnClickListener {navController.navigate(R.id.client_user_plan_overview_fragment) }

        authViewModel.user.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            binding.name.text = it.name

            if (it.avatar != null) {
                val bitmap = ImageUtils.convertBase64ToBitmap(it.avatar!!)
                if (bitmap != null) binding.avatar.setImageBitmap(bitmap)
            }
        }
    }

    private fun loadMetrics() {
        viewModel.getStatistics()
        viewModel.clientStatisticsData.observe(viewLifecycleOwner) { asyncData ->
            if (asyncData.status == AsyncData.Status.SUCCESS && asyncData.data != null) {
                val metrics = asyncData.data
                binding.trainingPlansCount.text = metrics.number_of_associated_plans.toString()
                binding.metricsCount.text = metrics.number_of_metrics.toString()
            }
        }
    }
}
