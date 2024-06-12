package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.UserPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class UserPlanAdapter(dataset: List<UserPlan>): BaseRecyclerAdapter<UserPlan, UserPlanAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.plan_name)
    }
    override fun getItemLayout(): Int = R.layout.recycle_adapter_user_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: UserPlan) {
        holder.name.text = item.plan.name
    }
}

