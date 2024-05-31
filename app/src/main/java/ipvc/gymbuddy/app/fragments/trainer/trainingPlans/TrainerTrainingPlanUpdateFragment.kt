package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanUpdateBinding
import ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan.TrainerTrainingPlanUpdateViewModel

class TrainerTrainingPlanUpdateFragment : BaseFragment<FragmentTrainerTrainingPlanUpdateBinding>(
    FragmentTrainerTrainingPlanUpdateBinding::inflate) {

    private lateinit var viewModel: TrainerTrainingPlanUpdateViewModel
    private var trainingPlanId: String? = null
    private lateinit var nameInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        arguments?.let {
            trainingPlanId = it.getString("trainingPlanId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadToolbar(getString(R.string.edit_training_plan))

        resetView()

        viewModel = ViewModelProvider(this)[TrainerTrainingPlanUpdateViewModel::class.java]

        nameInput = view.findViewById(R.id.name_input)

        trainingPlanId?.let { id ->
            viewModel.getTrainingPlan(id).observe(viewLifecycleOwner) { trainingPlan ->
                trainingPlan?.let { plan ->
                    nameInput.setText(plan.data?.name)
                }
            }
        }

        binding.submit.setOnClickListener { handleSubmit() }
        viewModel.updateData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    binding.message.text = getString(R.string.updated_successfully)
                    binding.message.setTextColor(requireActivity().getColor(R.color.secondaryDarkColor))
                    binding.message.visibility = View.VISIBLE
                }
                AsyncData.Status.ERROR -> {
                    binding.message.text = getString(R.string.something_went_wrong)
                    binding.message.setTextColor(requireActivity().getColor(R.color.error))
                    binding.message.visibility = View.VISIBLE
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
            }
        }

    }

    private fun handleSubmit() {
        trainingPlanId?.let { id ->
            val updatedName = nameInput.text.toString()
            viewModel.updateTrainingPlan(id, updatedName)
        }
    }

    private fun resetView() {
        binding.name.error = null
        binding.name.editText?.text = null
        binding.submit.isEnabled = true
        binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }
}