package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlansOverviewBinding
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan.TrainerTrainingPlanOverviewViewModel

class TrainerTrainingPlansOverviewFragment : BaseFragment<FragmentTrainerTrainingPlansOverviewBinding>(
    FragmentTrainerTrainingPlansOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.training_plans_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.userPlanPost.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.SUCCESS -> viewModel.getTrainingPlans()
                else -> {}
            }
        }

        viewModel.getTrainingPlans()
        viewModel.trainingPlansData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val adapter = TrainingPlanAdapter(it.data ?: listOf())
                    adapter.setOnTrainingPlanDeleteListener { trainingPlan ->
                        showDeleteConfirmationDialog(trainingPlan)
                    }
                    recyclerView.adapter = adapter
                }

            }
        }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
        binding.createPlan.setOnClickListener { navController.navigate(R.id.trainer_trainingplans_create_fragment) }
    }
    private fun handleSearch(search: String) {
        val filtered = viewModel.trainingPlansData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = TrainingPlanAdapter(filtered)
    }

    private fun showDeleteConfirmationDialog(trainingPlan: TrainingPlan) {
        if (NetworkUtils.isOffline(requireContext())) {
            navController.navigate(R.id.trainer_offline_fragment)
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, trainingPlan.name))
            setPositiveButton(getString(R.string.delete) ) { _, _ ->
                viewModel.deleteTrainingPlan(trainingPlan.id)
            }
            setNegativeButton(getString(R.string.cancel) ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}