package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.CategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentClientTrainingPlanExerciseIndividualBinding
import ipvc.gymbuddy.app.utils.ImageUtils

class ClientTrainingPlanExerciseIndividualFragment : BaseFragment<FragmentClientTrainingPlanExerciseIndividualBinding>(
    FragmentClientTrainingPlanExerciseIndividualBinding::inflate
) {

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

        loadToolbar(planExercise!!.exercise.name)

        binding.exerciseName.text = planExercise!!.exercise.name
        binding.machineName.text = planExercise!!.exercise.machine.name

        binding.reps.text = getString(R.string.reps, planExercise!!.repetitions)
        binding.sets.text = getString(R.string.sets, planExercise!!.sets)
        binding.rest.text = getString(R.string.rest, planExercise!!.rest_between_sets)

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW)
        categoriesRecyclerView.adapter = CategoryAdapter(planExercise!!.exercise.categories, CategoryAdapter.Direction.ROW)

        if (planExercise!!.exercise.photo != null){
            Glide.with(requireContext())
                .load(ImageUtils.convertBase64ToBitmap(planExercise!!.exercise.photo!!) ?: planExercise!!.exercise.photo)
                .placeholder(R.drawable.no_image)
                .into(binding.exerciseImage)
        }

        Glide.with(requireContext())
            .load(ImageUtils.convertBase64ToBitmap(planExercise!!.exercise.machine.photo) ?: planExercise!!.exercise.machine.photo)
            .placeholder(R.drawable.no_image)
            .into(binding.machineImage)

        binding.viewMachine.setOnClickListener {
            navController.navigate(
                R.id.client_machine_individual_fragment,
                bundleOf("data" to Gson().toJson(planExercise!!.exercise.machine))
            )
        }
    }
}
