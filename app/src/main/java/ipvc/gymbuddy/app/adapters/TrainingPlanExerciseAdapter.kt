package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class TrainingPlanExerciseAdapter(dataset: List<PlanExercise>): BaseRecyclerAdapter<PlanExercise, TrainingPlanExerciseAdapter.ViewHolder>(dataset) {

    private var onPlanExerciseDeleteListener: ((PlanExercise) -> Unit)? = null
    class ViewHolder(view: View) : BaseViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val machineName: TextView = view.findViewById(R.id.machine_name)
        val setsAndReps: TextView = view.findViewById(R.id.sets_and_reps)
        val exercisePhoto: ImageView = view.findViewById(R.id.exercise_image)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_plan_exercise)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_training_plan_exercise

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: PlanExercise) {
        val context = holder.itemView.context

        holder.exerciseName.text = item.exercise.name
        holder.machineName.text = item.exercise.machine.name

        val setsText = context.getString(R.string.sets)
        val repsText = context.getString(R.string.reps)
        val setsAndRepsText = "${item.sets} $setsText - ${item.repetitions} $repsText"
        holder.setsAndReps.text = setsAndRepsText

        Glide.with(context)
            .load(item.exercise.photo)
            .placeholder(R.drawable.baseline_assignment_24)
            .into(holder.exercisePhoto)


        holder.deleteButton.setOnClickListener {
            onPlanExerciseDeleteListener?.invoke(item)
        }
    }

    fun setOnTrainingPlanDeleteListener(listener: (PlanExercise) -> Unit) {
        onPlanExerciseDeleteListener = listener
    }
}
