package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanExerciseAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanExercisesOverviewBinding
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseOverviewModel

class TrainerTrainingPlanExercisesOverviewFragment : BaseFragment<FragmentTrainerTrainingPlanExercisesOverviewBinding>(
    FragmentTrainerTrainingPlanExercisesOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanExerciseOverviewModel
    private lateinit var recyclerView: RecyclerView
    private var trainingPlan: TrainingPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        arguments?.let {
            val trainingPlanJson = it.getString("trainingPlan")
            trainingPlan = Gson().fromJson(trainingPlanJson, TrainingPlan::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingPlan?.let { loadToolbar(it.name) }

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        Log.d("Training Plan id", trainingPlan!!.id)


        trainingPlan?.id?.let { planId ->
            viewModel.getPlanExercises(planId)
        }

        viewModel.planExerciseData.observe(viewLifecycleOwner) { resource ->
            resource.data?.let { exercises ->
                val groupedExercises = exercises.groupBy { it.day }
                val sortedExercises = groupedExercises.toSortedMap().values.flatten()
                val adapter = TrainingPlanExerciseAdapter(sortedExercises)
                recyclerView.adapter = adapter
            }
        }

        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.planExerciseData.value?.data?.filter {
            it.exercise.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = TrainingPlanExerciseAdapter(filtered)
    }

}