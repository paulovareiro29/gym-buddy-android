package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.UserPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class ClientUserPlanAdapter(dataset: List<UserPlan>) : BaseRecyclerAdapter<UserPlan, ClientUserPlanAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val name: TextView = view.findViewById(R.id.plan_name)
        val viewPlan: ImageButton = view.findViewById(R.id.view_plan)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_user_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: UserPlan, position: Int) {
        holder.name.text = item.plan.name
        holder.viewPlan.setOnClickListener { handleViewPlan(holder, item) }
    }

    private fun handleViewPlan(holder: ViewHolder, item: UserPlan) {
        holder.itemView.findNavController().navigate(
            R.id.client_trainingplan_exercises_overview_fragment,
            bundleOf("trainingPlan" to Gson().toJson(item.plan))
        )
    }
}
