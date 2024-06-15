package ipvc.gymbuddy.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R

class ClientTrainingPlanExerciseAdapter(private val exercises: List<PlanExercise>) : RecyclerView.Adapter<ClientTrainingPlanExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val machineName: TextView = view.findViewById(R.id.machine_name)
        val setsAndReps: TextView = view.findViewById(R.id.sets_and_reps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_adapter_client_training_plan_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseName.text = exercise.exercise.name
        holder.machineName.text = exercise.exercise.machine.name
        holder.setsAndReps.text = "${exercise.sets} Sets - ${exercise.repetitions} Reps"
    }

    override fun getItemCount() = exercises.size
}
