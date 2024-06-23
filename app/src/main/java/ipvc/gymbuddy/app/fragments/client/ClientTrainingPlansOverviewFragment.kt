package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ClientUserPlanAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentClientUserPlanOverviewBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientPlanViewModel

class ClientTrainingPlansOverviewFragment : BaseFragment<FragmentClientUserPlanOverviewBinding>(
    FragmentClientUserPlanOverviewBinding::inflate
) {

    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var viewModel: TrainerClientPlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        authViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.training_plans_overview))

        loadPlans()
    }

    private fun loadPlans() {
        viewModel.getUserPlans(authViewModel.user.value!!.id)
        viewModel.userPlans.observe(viewLifecycleOwner) { asyncData ->
            when(asyncData.status) {
                AsyncData.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    binding.recyclerView.adapter = ClientUserPlanAdapter(asyncData.data?.sortedByDescending { it.start_date } ?: listOf())
                }
            }
        }
    }
}
