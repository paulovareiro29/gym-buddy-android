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

class ClientTrainingPlanExerciseAdapter(private val exercises: List<PlanExercise>) : RecyclerView.Adapter<ClientTrainingPlanExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val machineName: TextView = view.findViewById(R.id.machine_name)
        val setsAndReps: TextView = view.findViewById(R.id.sets_and_reps)
        val exerciseImage: ImageView = view.findViewById(R.id.exercise_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_adapter_client_training_plan_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        val context = holder.itemView.context
        val sets = context.getString(R.string.sets, exercise.sets)
        val repetitions = context.getString(R.string.reps, exercise.repetitions)
        val setsAndRepsText = "$sets - $repetitions"

        holder.exerciseName.text = exercise.exercise.name
        holder.machineName.text = exercise.exercise.machine.name
        holder.setsAndReps.text = setsAndRepsText

        Glide.with(context)
            .load(exercise.exercise.photo)
            .placeholder(R.drawable.baseline_fitness_center_24)
            .into(holder.exerciseImage)
    }

    override fun getItemCount() = exercises.size
}
