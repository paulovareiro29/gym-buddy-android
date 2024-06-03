package ipvc.gymbuddy.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R

class TrainingPlanExerciseAdapter(private val dataset: List<PlanExercise>) :
    RecyclerView.Adapter<TrainingPlanExerciseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_adapter_training_plan_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = dataset[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = dataset.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
        private val machineName: TextView = itemView.findViewById(R.id.machine_name)
        private val setsAndReps: TextView = itemView.findViewById(R.id.sets_and_reps)
        private val exercisePhoto: ImageView = itemView.findViewById(R.id.exercise_image)
        private val dayTitle: TextView = itemView.findViewById(R.id.day_header)

        fun bind(exercise: PlanExercise) {
            exerciseName.text = exercise.exercise.name
            machineName.text = exercise.exercise.machine.name

            val context = itemView.context
            val setsText = context.getString(R.string.sets)
            val repsText = context.getString(R.string.reps)
            val setsAndRepsText = "${exercise.sets} $setsText - ${exercise.repetitions} $repsText"
            setsAndReps.text = setsAndRepsText

            Glide.with(context)
                .load(exercise.exercise.photo)
                .placeholder(R.drawable.baseline_assignment_24)
                .into(exercisePhoto)

            if (adapterPosition == 0 || dataset[adapterPosition - 1].day != exercise.day) {
                dayTitle.visibility = View.VISIBLE
                dayTitle.text = buildString {
                    append(context.getString(R.string.day))
                    append(" ${exercise.day}")
                }

            } else {
                dayTitle.visibility = View.GONE
            }
        }
    }
}
