package ipvc.gymbuddy.app.fragments.trainer

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerHomeBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.trainer.TrainerHomeViewModel

class TrainerHomeFragment : BaseFragment<FragmentTrainerHomeBinding>(
    FragmentTrainerHomeBinding::inflate
) {
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var viewModel: TrainerHomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = getViewModel()
        viewModel = getViewModel()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.home), true)

        loadMetrics()

        binding.trainingPlans.setOnClickListener { navController.navigate(R.id.trainer_trainingplans_overview_fragment) }
        binding.listClients.setOnClickListener { navController.navigate(R.id.trainer_listclients_overview_fragment) }
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
        viewModel.userStatistics.observe(viewLifecycleOwner) { asyncData ->
            if (asyncData.status == AsyncData.Status.SUCCESS && asyncData.data != null) {
                val metrics = asyncData.data
                binding.clientsCount.text = metrics.number_of_contracts.toString()
                binding.trainingPlansCount.text = metrics.number_of_created_plans.toString()
            }
        }
    }
}
