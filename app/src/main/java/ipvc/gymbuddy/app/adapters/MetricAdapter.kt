package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class MetricAdapter(dataset: List<Metric>): BaseRecyclerAdapter<Metric, MetricAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val metricName: TextView = view.findViewById(R.id.metric_name)
        val metricCreator: TextView = view.findViewById(R.id.metric_creator)
        val metricValue: TextView = view.findViewById(R.id.metric_value)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_metrics

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Metric) {
        holder.metricName.text = item.metricType?.name ?: "Unknown Type"
        holder.metricCreator.text = item.creator.name
        holder.metricValue.text = item.value.toString()
    }
}
