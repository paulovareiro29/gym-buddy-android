package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanUpdateBinding
import ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan.TrainerTrainingPlanUpdateViewModel

class TrainerTrainingPlanUpdateFragment : BaseFragment<FragmentTrainerTrainingPlanUpdateBinding>(
    FragmentTrainerTrainingPlanUpdateBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}