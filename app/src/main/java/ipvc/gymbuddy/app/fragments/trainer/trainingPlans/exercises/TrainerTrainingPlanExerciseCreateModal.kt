package ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises

import Modal
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseCreateViewModel

class TrainerTrainingPlanExerciseCreateModal : Modal(R.layout.fragment_trainer_training_plan_exercise_create_modal) {

    interface ExerciseCreationListener {
        fun onExerciseCreated()
    }

    private lateinit var viewModel: TrainerTrainingPlanExerciseCreateViewModel
    private var trainingPlanId: String? = null

    private lateinit var exerciseCreationListener: ExerciseCreationListener

    private lateinit var title: TextView
    private lateinit var exercise: AutoCompleteTextView
    private lateinit var sets: TextInputLayout
    private lateinit var repetitions: TextInputLayout
    private lateinit var restBetweenSets: TextInputLayout
    private lateinit var day: TextInputLayout
    private lateinit var submitButton: Button
    private lateinit var messageTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TrainerTrainingPlanExerciseCreateViewModel::class.java]
        trainingPlanId = arguments?.getString("trainingPlanId")

        if (trainingPlanId == null) {
            this.dismiss()
            return
        }

        title = view.findViewById(R.id.modal_title)
        exercise = view.findViewById(R.id.exercise)
        sets = view.findViewById(R.id.sets)
        repetitions = view.findViewById(R.id.repetitions)
        restBetweenSets = view.findViewById(R.id.rest_between_sets)
        day = view.findViewById(R.id.day)
        submitButton = view.findViewById(R.id.submit_button)
        messageTextView = view.findViewById(R.id.message)

        resetView()
        loadExercises()

        submitButton.setOnClickListener { handleSubmit() }
        viewModel.postData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    submitButton.isEnabled = false
                    submitButton.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    showMessage(getString(R.string.created_successfully), R.color.secondaryDarkColor)
                    exerciseCreationListener.onExerciseCreated()
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
        val planExercise = (exercise.adapter as DropdownAdapter).selected
        val exerciseSets = sets.editText!!.text.toString()
        val exerciseReps = repetitions.editText!!.text.toString()
        val exerciseRest = restBetweenSets.editText!!.text.toString()
        val exerciseDay = day.editText!!.text.toString()

        exercise.error = null
        sets.error = null
        repetitions.error = null
        restBetweenSets.error = null
        day.error = null

        if (!Validator.validateRequiredField(exercise, requireContext())) {
            return
        }

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

        viewModel.createPlanExercise(
            trainingPlanId!!,
            planExercise!!.id,
            exerciseReps.toInt(),
            exerciseSets.toInt(),
            exerciseRest.toInt(),
            exerciseDay
        )
    }

    private fun loadExercises() {
        viewModel.getExercises()

        viewModel.exercisesData.observe(viewLifecycleOwner){ response ->
            if (response.data != null) {
                val adapter = DropdownAdapter(requireContext(), exercise, response.data.map { DropdownItem(it.id, it.name) })
                exercise.setAdapter(adapter)
            }
        }
    }

    private fun resetView() {
        exercise.error = null
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

    fun setExerciseCreationListener(listener: ExerciseCreationListener) {
        this.exerciseCreationListener = listener
    }
}
