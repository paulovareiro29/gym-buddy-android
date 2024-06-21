package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.UserPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.DateUtils
import ipvc.gymbuddy.app.utils.StringUtils
import java.util.Date

class ClientUserPlanAdapter(dataset: List<UserPlan>): BaseRecyclerAdapter<UserPlan, ClientUserPlanAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.plan_name)
        val status: TextView = view.findViewById(R.id.status)
        val viewPlan: ImageButton = view.findViewById(R.id.view_plan)
    }
    override fun getItemLayout(): Int = R.layout.recycle_adapter_user_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: UserPlan, position: Int) {
        holder.name.text = StringUtils.capitalize(item.plan.name)
        holder.viewPlan.setOnClickListener { handleViewPlan(holder, item) }

        val today = Date()
        when {
            today.after(item.start_date) && today.before(item.end_date) -> {
                holder.status.text = holder.itemView.context.getString(R.string.ongoing_until, DateUtils.formatDateFromIso8601(item.end_date))
                holder.image.visibility = View.VISIBLE
            }
            today.before(item.start_date) -> {
                holder.status.text = holder.itemView.context.getString(
                    R.string.starts_on,
                    DateUtils.formatDateFromIso8601(item.start_date)
                )
            }
            today.after(item.end_date) -> {
                holder.status.text = holder.itemView.context.getString(
                    R.string.ended_on,
                    DateUtils.formatDateFromIso8601(item.end_date)
                )
            }
            else -> {
                holder.status.visibility = View.GONE
            }
        }
    }

    private fun handleViewPlan(holder: ViewHolder, item: UserPlan) {
        holder.itemView.findNavController().navigate(
            R.id.client_trainingplan_exercises_overview_fragment,
            bundleOf("trainingPlan" to Gson().toJson(item.plan))
        )
    }
}

