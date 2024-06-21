package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.ImageUtils

class ClientTrainingPlanExerciseAdapter(dataset: List<PlanExercise>)
    : BaseRecyclerAdapter<PlanExercise, ClientTrainingPlanExerciseAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val machineName: TextView = view.findViewById(R.id.machine_name)
        val setsAndReps: TextView = view.findViewById(R.id.sets_and_reps)
        val exercisePhoto: ImageView = view.findViewById(R.id.exercise_image)
        val viewButton: ImageButton = view.findViewById(R.id.view_exercise)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_client_training_plan_exercise

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: PlanExercise, position: Int) {
        val context = holder.itemView.context
        val sets = context.getString(R.string.sets, item.sets)
        val repetitions = context.getString(R.string.reps, item.repetitions)
        val setsAndRepsText = "$sets - $repetitions"

        holder.exerciseName.text = item.exercise.name
        holder.machineName.text = item.exercise.machine.name
        holder.setsAndReps.text = setsAndRepsText

        if (item.exercise.photo != null) {
            val bitmap = ImageUtils.convertBase64ToBitmap(item.exercise.photo!!)
            if (bitmap != null) {
                holder.exercisePhoto.setImageBitmap(bitmap)
            }
        }

        holder.viewButton.setOnClickListener {
            val planExerciseJson = Gson().toJson(item)
            holder.itemView.findNavController().navigate(
                R.id.client_trainingplan_exercise_individual_fragment,
                bundleOf("planExercise" to planExerciseJson)
            )
        }
    }
}