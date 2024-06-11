package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.DateUtils

class MetricAdapter(dataset: List<Metric>): BaseRecyclerAdapter<Metric, MetricAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val creator: TextView = view.findViewById(R.id.creator)
        val value: TextView = view.findViewById(R.id.value)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_metrics

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Metric) {
        holder.name.text = item.type?.name ?: holder.itemView.context.getString(R.string.unknown_type)
        holder.creator.text = holder.itemView.context.getString(R.string.submitted_by, item.creator.name)
        holder.value.text = item.value
    }

}
