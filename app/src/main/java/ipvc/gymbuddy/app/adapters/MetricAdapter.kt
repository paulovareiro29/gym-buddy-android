package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.DateUtils

class MetricAdapter(private val dataset: List<Metric>) : BaseRecyclerAdapter<Metric, MetricAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val date: TextView = view.findViewById(R.id.date)
        val name: TextView = view.findViewById(R.id.name)
        val creator: TextView = view.findViewById(R.id.creator)
        val value: TextView = view.findViewById(R.id.value)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_metrics
    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Metric, position: Int) {
        holder.name.text = item.type?.name
        holder.creator.text = holder.itemView.context.getString(R.string.submitted_by, item.creator.name)
        holder.value.text = item.value

        if(dataset.isEmpty()) return
        if (position > 0) {
            val previous = dataset[position - 1]
            if (DateUtils.isSameDay(item.date, previous.date)) {
                holder.date.visibility = View.GONE
            } else {
                holder.date.visibility = View.VISIBLE
                holder.date.text = DateUtils.formatDateFromIso8601(item.date)
            }
        } else {
            holder.date.visibility = View.VISIBLE
            holder.date.text = DateUtils.formatDateFromIso8601(item.date)
        }
    }
}
