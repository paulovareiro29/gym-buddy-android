package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.CategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanExerciseIndividualBinding

class TrainerTrainingPlanExerciseIndividualFragment : BaseFragment<FragmentTrainerTrainingPlanExerciseIndividualBinding>(
    FragmentTrainerTrainingPlanExerciseIndividualBinding::inflate) {

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

        if (planExercise == null) {
            navController.navigateUp()
            return
        }

        loadToolbar(planExercise!!.plan.name)

        binding.exerciseName.text = planExercise!!.exercise.name
        binding.machineName.text = planExercise!!.exercise.machine.name

        binding.reps.text = getString(R.string.reps, planExercise!!.repetitions)
        binding.sets.text = getString(R.string.sets, planExercise!!.sets)
        binding.rest.text = getString(R.string.rest, planExercise!!.rest_between_sets)

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW)
        categoriesRecyclerView.adapter = CategoryAdapter(planExercise!!.exercise.categories, CategoryAdapter.Direction.ROW)

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