package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlansOverviewBinding
import ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan.TrainerTrainingPlanOverviewViewModel

class TrainerTrainingPlansOverviewFragment : BaseFragment<FragmentTrainerTrainingPlansOverviewBinding>(
    FragmentTrainerTrainingPlansOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.training_plans_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getTrainingPlans()
        viewModel.trainingPlansData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = TrainingPlanAdapter(it.data)
            }
        }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }

        binding.createTrainingPlan.setOnClickListener { navController.navigate(R.id.trainer_trainingplans_create_fragment) }
    }
    private fun handleSearch(search: String) {
        val filtered = viewModel.trainingPlansData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = TrainingPlanAdapter(filtered)
    }
}