package ipvc.gymbuddy.app.adapters

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.fragments.trainer.trainingPlans.exercises.TrainerTrainingPlanExerciseUpdateModal

class TrainingPlanExerciseAdapter(private val fragmentManager: FragmentManager, private val trainingPlanId: String, dataset: List<PlanExercise>)
    : BaseRecyclerAdapter<PlanExercise, TrainingPlanExerciseAdapter.ViewHolder>(dataset) {

    private var onPlanExerciseDeleteListener: ((PlanExercise) -> Unit)? = null
    class ViewHolder(view: View) : BaseViewHolder(view) {

        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val machineName: TextView = view.findViewById(R.id.machine_name)
        val setsAndReps: TextView = view.findViewById(R.id.sets_and_reps)
        val exercisePhoto: ImageView = view.findViewById(R.id.exercise_image)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_plan_exercise)
        val editButton: ImageButton = view.findViewById(R.id.edit_plan_exercise)
        val viewButton: ImageButton = view.findViewById(R.id.view_exercise)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_training_plan_exercise

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: PlanExercise) {
        val context = holder.itemView.context
        val sets = context.getString(R.string.sets, item.sets)
        val repetitions = context.getString(R.string.reps, item.repetitions)
        val setsAndRepsText = "$sets - $repetitions"

        holder.exerciseName.text = item.exercise.name
        holder.machineName.text = item.exercise.machine.name
        holder.setsAndReps.text = setsAndRepsText

        Glide.with(context)
            .load(item.exercise.photo)
            .placeholder(R.drawable.baseline_assignment_24)
            .into(holder.exercisePhoto)

        holder.editButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("trainingPlanId", trainingPlanId)
                putString("planExercise", Gson().toJson(item))
                putString("title", context.getString(R.string.add_client_to, item.exercise.name))
            }
            val dialogFragment = TrainerTrainingPlanExerciseUpdateModal().apply {
                arguments = bundle
            }
            dialogFragment.show(fragmentManager, "TrainerTrainingPlanExerciseCreateModal")
        }

        holder.deleteButton.setOnClickListener {
            onPlanExerciseDeleteListener?.invoke(item)
        }

        holder.viewButton.setOnClickListener {
            val planExerciseJson = Gson().toJson(item)
            holder.itemView.findNavController().navigate(
                R.id.trainer_trainingplan_exercise_overview_fragment,
                bundleOf("planExercise" to planExerciseJson)
            )
        }
    }

    fun setOnTrainingPlanDeleteListener(listener: (PlanExercise) -> Unit) {
        onPlanExerciseDeleteListener = listener
    }
}
