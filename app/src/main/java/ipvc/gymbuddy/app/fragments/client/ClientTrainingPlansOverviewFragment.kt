package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.adapters.ClientUserPlanAdapter
import ipvc.gymbuddy.app.databinding.FragmentClientUserPlanOverviewBinding
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientPlanViewModel

class ClientTrainingPlansOverviewFragment : BaseFragment<FragmentClientUserPlanOverviewBinding>(
    FragmentClientUserPlanOverviewBinding::inflate
) {

    private lateinit var viewModel: TrainerClientPlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.training_plans_overview))

        val userId = arguments?.getString("userId") ?: throw IllegalStateException("User ID must be passed to ClientTrainingPlansOverviewFragment")

        viewModel.getUserPlans(userId)
        viewModel.userPlans.observe(viewLifecycleOwner) { asyncData ->
            if (asyncData.data != null) {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = ClientUserPlanAdapter(asyncData.data)
            }
        }
    }

}
