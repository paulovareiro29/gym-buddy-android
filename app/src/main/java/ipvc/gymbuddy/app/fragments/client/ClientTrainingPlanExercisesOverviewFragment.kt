package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.adapters.ClientTrainingPlanExerciseAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.databinding.FragmentClientTrainingPlanExercisesOverviewBinding
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseOverviewModel

class ClientTrainingPlanExercisesOverviewFragment : Fragment() {
    private var _binding: FragmentClientTrainingPlanExercisesOverviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrainerTrainingPlanExerciseOverviewModel
    private var trainingPlan: TrainingPlan? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentClientTrainingPlanExercisesOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TrainerTrainingPlanExerciseOverviewModel::class.java]

        val planJson = requireArguments().getString("trainingPlan")
        trainingPlan = Gson().fromJson(planJson, TrainingPlan::class.java)

        viewModel.getPlanExercises(trainingPlan!!.id)
        viewModel.planExerciseData.observe(viewLifecycleOwner) { asyncData ->
            if (asyncData.status == AsyncData.Status.SUCCESS) {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                asyncData.data?.let {
                    binding.recyclerView.adapter = ClientTrainingPlanExerciseAdapter(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
