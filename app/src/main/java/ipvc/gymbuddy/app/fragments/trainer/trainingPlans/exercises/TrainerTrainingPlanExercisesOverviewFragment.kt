package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import android.os.Bundle
import android.view.View
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanExerciseAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanExercisesOverviewBinding
import ipvc.gymbuddy.app.fragments.ui.TabRecyclerViewFragment
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseOverviewModel

class TrainerTrainingPlanExercisesOverviewFragment : BaseFragment<FragmentTrainerTrainingPlanExercisesOverviewBinding>(
    FragmentTrainerTrainingPlanExercisesOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanExerciseOverviewModel
    private var trainingPlan: TrainingPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        arguments?.let {
            trainingPlan = Gson().fromJson(it.getString("trainingPlan"), TrainingPlan::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (trainingPlan == null) navController.navigateUp()

        loadToolbar(trainingPlan!!.name)

        viewModel.getPlanExercises(trainingPlan!!.id)

        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        viewModel.planExerciseData.observe(viewLifecycleOwner) { resource ->
            val exercises = resource.data ?: return@observe
            val uniqueDays = exercises.map { it.day }.distinct()

            val fragments = uniqueDays.map { day ->
                val dayExercises = exercises.filter { it.day == day }
                TabRecyclerViewFragment(TrainingPlanExerciseAdapter(dayExercises))
            }

            viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount() = fragments.size
                override fun createFragment(position: Int) = fragments[position]
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = uniqueDays[position].toString()
            }.attach()
        }

        binding.createExercise.setOnClickListener {
            val bundle = Bundle().apply {
                putString("trainingPlanId", trainingPlan?.id)
            }
            val dialogFragment = TrainerTrainingPlanExerciseCreateModal().apply {
                arguments = bundle
                setExerciseCreationListener(object :
                    TrainerTrainingPlanExerciseCreateModal.ExerciseCreationListener {
                    override fun onExerciseCreated() {
                        updateExerciseList()
                    }
                })
            }
            dialogFragment.show(childFragmentManager, "TrainerTrainingPlanExerciseCreateModal")
        }
    }

    private fun updateExerciseList() {
        trainingPlan?.id?.let { planId ->
            viewModel.getPlanExercises(planId)
        }
    }
}