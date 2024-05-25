package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlansBinding
import ipvc.gymbuddy.app.viewmodels.trainer.TrainingPlanOverviewViewModel

class TrainingPlansOverviewFragment : BaseFragment<FragmentTrainerTrainingPlansBinding>(
    FragmentTrainerTrainingPlansBinding::inflate) {

    private lateinit var viewModel: TrainingPlanOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.trainingPlans_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getTrainingPlans()
        viewModel.trainingPlansData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = TrainingPlanAdapter(it.data)
            }
        }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }
    private fun handleSearch(search: String) {
        val filtered = viewModel.trainingPlansData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = TrainingPlanAdapter(filtered)
    }
}
