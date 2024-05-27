package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanUpdateBinding
import ipvc.gymbuddy.app.viewmodels.trainer.TrainerTrainingPlanUpdateViewModel

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