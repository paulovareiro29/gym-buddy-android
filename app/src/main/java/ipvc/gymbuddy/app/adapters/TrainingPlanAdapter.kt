package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class TrainingPlanAdapter(dataset: List<TrainingPlan>): BaseRecyclerAdapter<TrainingPlan, TrainingPlanAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.plan_name)
        val editButton: ImageButton = view.findViewById(R.id.edit_plan)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_training_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: TrainingPlan) {
        holder.name.text = item.name
        holder.editButton.setOnClickListener {
            val bundle = bundleOf("trainingPlanId" to item.id)
            holder.itemView.findNavController().navigate(R.id.trainer_trainingplans_update_fragment, bundle)
        }
    }
}