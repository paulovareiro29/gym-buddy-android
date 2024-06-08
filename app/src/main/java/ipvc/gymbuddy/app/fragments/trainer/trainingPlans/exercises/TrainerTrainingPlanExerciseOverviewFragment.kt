package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.CategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanExerciseOverviewBinding

class TrainerTrainingPlanExerciseOverviewFragment : BaseFragment<FragmentTrainerTrainingPlanExerciseOverviewBinding>(
    FragmentTrainerTrainingPlanExerciseOverviewBinding::inflate) {

    private lateinit var categoriesRecyclerView: RecyclerView
    private var planExercise: PlanExercise? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            planExercise = Gson().fromJson(it.getString("planExercise"), PlanExercise::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (planExercise == null) navController.navigateUp()

        loadToolbar(planExercise!!.plan.name)

        binding.exerciseName.text = planExercise!!.exercise.name
        binding.machineName.text = planExercise!!.exercise.machine.name

        val repsText = getString(R.string.reps, planExercise!!.repetitions)
        val setsText = getString(R.string.sets, planExercise!!.sets)
        val restText = getString(R.string.rest, planExercise!!.rest_between_sets)

        binding.reps.text = repsText
        binding.sets.text = setsText
        binding.rest.text = restText

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecyclerView.adapter = CategoryAdapter(planExercise!!.exercise.categories)

        context?.let {
            Glide.with(it)
                .load(planExercise!!.exercise.photo)
                .placeholder(R.drawable.no_image)
                .into(binding.exerciseImage)

            Glide.with(it)
                .load(planExercise!!.exercise.machine.photo)
                .placeholder(R.drawable.no_image)
                .into(binding.machineImage)
        }

    }
}