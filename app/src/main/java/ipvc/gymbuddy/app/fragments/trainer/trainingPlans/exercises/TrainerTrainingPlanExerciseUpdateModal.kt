package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import Modal
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseUpdateViewModel


class TrainerTrainingPlanExerciseUpdateModal : Modal(R.layout.fragment_trainer_training_plan_exercise_update_modal) {

    private lateinit var viewModel: TrainerTrainingPlanExerciseUpdateViewModel
    private var trainingPlanId: String? = null
    private var planExercise: PlanExercise? = null

    private lateinit var title: TextView
    private lateinit var sets: TextInputLayout
    private lateinit var repetitions: TextInputLayout
    private lateinit var restBetweenSets: TextInputLayout
    private lateinit var day: TextInputLayout
    private lateinit var submitButton: Button
    private lateinit var messageTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TrainerTrainingPlanExerciseUpdateViewModel::class.java]
        trainingPlanId = arguments?.getString("trainingPlanId")
        planExercise = Gson().fromJson(arguments?.getString("planExercise"), PlanExercise::class.java)
        val titleText = arguments?.getString("title") ?: ""

        if(trainingPlanId == null || planExercise == null){
            this.dismiss()
            return
        }

        title = view.findViewById(R.id.modal_title)
        sets = view.findViewById(R.id.sets)
        repetitions = view.findViewById(R.id.repetitions)
        restBetweenSets = view.findViewById(R.id.rest_between_sets)
        day = view.findViewById(R.id.day)
        submitButton = view.findViewById(R.id.submit_button)
        messageTextView = view.findViewById(R.id.message)

        title.text = titleText
        sets.editText?.setText(planExercise!!.sets.toString())
        repetitions.editText?.setText(planExercise!!.repetitions.toString())
        restBetweenSets.editText?.setText(planExercise!!.rest_between_sets.toString())
        day.editText?.setText(planExercise!!.day)

        resetView()

        submitButton.setOnClickListener { handleSubmit() }
        viewModel.updateDate.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    submitButton.isEnabled = false
                    submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    showMessage(getString(R.string.updated_successfully), R.color.secondaryDarkColor)
                    viewModel.getPlanExercises(trainingPlanId ?: "")
                }
                AsyncData.Status.ERROR -> {
                    showMessage(getString(R.string.something_went_wrong), R.color.error)
                    submitButton.isEnabled = true
                    submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
                }
            }
        }
    }

    private fun handleSubmit() {
        val exerciseSets = sets.editText!!.text.toString()
        val exerciseReps = repetitions.editText!!.text.toString()
        val exerciseRest = restBetweenSets.editText!!.text.toString()
        val exerciseDay = day.editText!!.text.toString()

        sets.error = null
        repetitions.error = null
        restBetweenSets.error = null
        day.error = null

        if (!Validator.validateRequiredField(sets.editText!!, requireContext())) {
            return
        }

        if (!Validator.validateRequiredField(repetitions.editText!!, requireContext())) {
            return
        }

        if (!Validator.validateRequiredField(restBetweenSets.editText!!, requireContext())) {
            return
        }

        if (!Validator.validateRequiredField(day.editText!!, requireContext())) {
            return
        }

        viewModel.updatePlanExercise(
            trainingPlanId!!,
            planExercise!!.id,
            planExercise!!.exercise.id,
            exerciseReps.toInt(),
            exerciseSets.toInt(),
            exerciseRest.toInt(),
            exerciseDay
        )
    }

    private fun resetView() {
        sets.error = null
        repetitions.error = null
        restBetweenSets.error = null
        day.error = null
        submitButton.isEnabled = true
        submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        messageTextView.visibility = View.INVISIBLE
    }

    private fun showMessage(message: String, colorId: Int) {
        messageTextView.text = message
        messageTextView.setTextColor(requireActivity().getColor(colorId))
        messageTextView.visibility = View.VISIBLE
    }

}