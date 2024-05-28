package ipvc.gymbuddy.app.fragments.trainer.trainingPlans

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentTrainerTrainingPlanCreateBinding
import ipvc.gymbuddy.app.viewmodels.trainer.trainingPlan.TrainerTrainingPlanCreateViewModel

class TrainerTrainingPlanCreateFragment : BaseFragment<FragmentTrainerTrainingPlanCreateBinding>(
    FragmentTrainerTrainingPlanCreateBinding::inflate
) {
    private lateinit var viewModel : TrainerTrainingPlanCreateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.create_training_plan))

        resetView()

        binding.submit.setOnClickListener { handleSubmit() }
        viewModel.postData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    binding.message.text = getString(R.string.created_successfully)
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
        val name = binding.name.editText ?: return

        if (!Validator.validateRequiredField(name, requireContext())) return

        viewModel.createTrainingPlan(name.text.toString())
    }

    private fun resetView() {
        binding.name.error = null
        binding.name.editText?.text = null
        binding.submit.isEnabled = true
        binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }

}