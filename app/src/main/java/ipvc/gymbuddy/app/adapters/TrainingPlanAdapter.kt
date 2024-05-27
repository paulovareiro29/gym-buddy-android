package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class TrainingPlanAdapter(dataset: List<TrainingPlan>): BaseRecyclerAdapter<TrainingPlan, TrainingPlanAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.plan_name)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_training_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: TrainingPlan) {
        holder.name.text = item.name
    }
}