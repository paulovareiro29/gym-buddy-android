package ipvc.gymbuddy.app.fragments.trainer

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerHomeBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.trainer.TrainerHomeOverviewViewModel

class TrainerHomeFragment : BaseFragment<FragmentTrainerHomeBinding>(
    FragmentTrainerHomeBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var trainerViewModel: TrainerHomeOverviewViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        trainerViewModel = ViewModelProvider(this).get(TrainerHomeOverviewViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.home), true)

        binding.name.text = viewModel.user.value!!.name

        trainerViewModel.getUserMetrics()
        trainerViewModel.userMetricsData.observe(viewLifecycleOwner, Observer { asyncData ->
            if (asyncData.status == AsyncData.Status.SUCCESS && asyncData.data != null) {
                val metrics = asyncData.data
                binding.clientsCount.text = metrics.number_of_contracts.toString()
                binding.trainingPlansCount.text = metrics.number_of_created_plans.toString()
            }
        })

        binding.trainingPlans.setOnClickListener { navController.navigate(R.id.trainer_trainingplans_overview_fragment) }
    }

}
