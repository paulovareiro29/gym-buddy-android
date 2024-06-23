package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanExerciseAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanExercisesOverviewBinding
import ipvc.gymbuddy.app.fragments.ui.TabRecyclerViewFragment
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseOverviewModel

class TrainerTrainingPlanExercisesOverviewFragment : BaseFragment<FragmentTrainerTrainingPlanExercisesOverviewBinding>(
    FragmentTrainerTrainingPlanExercisesOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanExerciseOverviewModel
    private var trainingPlan: TrainingPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        try {
            trainingPlan = Gson().fromJson(arguments?.getString("trainingPlan"), TrainingPlan::class.java)
        } catch (_: JsonSyntaxException) {
            navController.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (trainingPlan == null) navController.navigateUp()

        loadToolbar(trainingPlan!!.name)
        loadExercises()

        binding.createExercise.setOnClickListener { handleCreateExercise() }
    }

    private fun loadExercises() {
        viewModel.getPlanExercises(trainingPlan!!.id)

        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        viewModel.planExerciseData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                AsyncData.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    viewPager.visibility = View.INVISIBLE
                    tabLayout.visibility = View.INVISIBLE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    val exercises = resource.data ?: return@observe
                    val uniqueDays = exercises.map { it.day }.distinct()

                    val fragments = uniqueDays.map { day ->
                        val dayExercises = exercises.filter { it.day == day }
                        val adapter = TrainingPlanExerciseAdapter(childFragmentManager, trainingPlan!!.id, dayExercises)
                        adapter.setOnTrainingPlanDeleteListener { planExercise ->
                            showDeleteConfirmationDialog(planExercise)
                        }
                        TabRecyclerViewFragment(adapter)
                    }

                    viewPager.adapter = object : FragmentStateAdapter(this) {
                        override fun getItemCount() = fragments.size
                        override fun createFragment(position: Int) = fragments[position]
                    }

                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        tab.text = uniqueDays[position]
                    }.attach()

                    viewPager.visibility = View.VISIBLE
                    tabLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun handleCreateExercise() {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.trainer_offline_fragment)
            return
        }

        val dialogFragment = TrainerTrainingPlanExerciseCreateModal().apply {
            arguments = Bundle().apply {
                putString("trainingPlanId", trainingPlan!!.id)
            }
            setExerciseCreationListener(object :
                TrainerTrainingPlanExerciseCreateModal.ExerciseCreationListener {
                override fun onExerciseCreated() {
                    updateExerciseList()
                }
            })
        }
        dialogFragment.show(childFragmentManager, "TrainerTrainingPlanExerciseCreateModal")
    }

    private fun updateExerciseList() {
        viewModel.getPlanExercises(trainingPlan!!.id)
    }

    private fun showDeleteConfirmationDialog(planExercise: PlanExercise) {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.trainer_offline_fragment)
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, planExercise.exercise.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                viewModel.deletePlanExercise(trainingPlan!!.id, planExercise.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}

