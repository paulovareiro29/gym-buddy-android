package ipvc.gymbuddy.app.adapters

import Modal
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class TrainingPlanAdapter(dataset: List<TrainingPlan>): BaseRecyclerAdapter<TrainingPlan, TrainingPlanAdapter.ViewHolder>(dataset) {

    private var onTrainingPlanDeleteListener: ((TrainingPlan) -> Unit)? = null

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.plan_name)
        val editButton: ImageButton = view.findViewById(R.id.edit_plan)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_plan)
        val addClientButton: ImageButton = view.findViewById(R.id.add_client)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_training_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: TrainingPlan) {
        holder.name.text = item.name
        holder.editButton.setOnClickListener {
            holder.itemView
                .findNavController()
                .navigate(
                    R.id.trainer_trainingplans_update_fragment,
                    bundleOf("trainingPlanId" to item.id)
                )
        }

        holder.addClientButton.setOnClickListener {
            val activity = it.context as FragmentActivity
            val title = activity.getString(R.string.add_client_to, item.name)
            val bundle = bundleOf("trainingPlan" to Gson().toJson(item))
            val dialogFragment = Modal.newInstance(title, "TrainerAddClientToPlanModal", bundle)
            dialogFragment.show(activity.supportFragmentManager, "TrainerAddClientToPlanModal")
        }


        holder.deleteButton.setOnClickListener {
            onTrainingPlanDeleteListener?.invoke(item)
        }
    }

    fun setOnTrainingPlanDeleteListener(listener: (TrainingPlan) -> Unit) {
        onTrainingPlanDeleteListener = listener
    }
}