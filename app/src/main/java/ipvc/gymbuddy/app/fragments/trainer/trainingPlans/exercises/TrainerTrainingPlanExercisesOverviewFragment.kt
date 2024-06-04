package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanExerciseAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanExercisesOverviewBinding
import ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises.TrainerTrainingPlanExerciseCreateModal.ExerciseCreationListener
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseDeleteViewModel
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseOverviewModel

class TrainerTrainingPlanExercisesOverviewFragment : BaseFragment<FragmentTrainerTrainingPlanExercisesOverviewBinding>(
    FragmentTrainerTrainingPlanExercisesOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanExerciseOverviewModel
    private lateinit var deleteViewModel: TrainerTrainingPlanExerciseDeleteViewModel
    private lateinit var recyclerView: RecyclerView
    private var trainingPlan: TrainingPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        deleteViewModel = getViewModel()
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

        trainingPlan?.id?.let { planId ->
            viewModel.getPlanExercises(planId)
        }
        viewModel.planExerciseData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                val adapter = TrainingPlanExerciseAdapter(it.data)
                adapter.setOnTrainingPlanDeleteListener { planExercise ->
                    showDeleteConfirmationDialog(planExercise)
                }
                recyclerView.adapter = adapter
            }
        }

        binding.createExercise.setOnClickListener {
            val bundle = Bundle().apply {
                putString("trainingPlanId", trainingPlan?.id)
            }
            val dialogFragment = TrainerTrainingPlanExerciseCreateModal().apply {
                arguments = bundle
                setExerciseCreationListener(object : ExerciseCreationListener {
                    override fun onExerciseCreated() {
                        updateExerciseList()
                    }
                })
            }
            dialogFragment.show(childFragmentManager, "TrainerTrainingPlanExerciseCreateModal")
        }

        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.planExerciseData.value?.data?.filter {
            it.exercise.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = TrainingPlanExerciseAdapter(filtered)
    }

    private fun updateExerciseList() {
        trainingPlan?.id?.let { planId ->
            viewModel.getPlanExercises(planId)
        }
    }

    private fun showDeleteConfirmationDialog(planExercise: PlanExercise) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, planExercise.exercise.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                deleteViewModel.deletePlanExercise(trainingPlan!!.id, planExercise.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
