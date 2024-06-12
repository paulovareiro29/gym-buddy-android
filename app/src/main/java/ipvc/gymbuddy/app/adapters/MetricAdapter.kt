package ipvc.gymbuddy.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R

class MetricAdapter(private var metrics: List<Metric>) : RecyclerView.Adapter<MetricAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val creator: TextView = itemView.findViewById(R.id.creator)
        val value: TextView = itemView.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_adapter_metrics, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = metrics[position]
        holder.name.text = item.type?.name
        holder.creator.text = holder.itemView.context.getString(R.string.submitted_by, item.creator.name)
        holder.value.text = item.value
    }

    override fun getItemCount(): Int {
        return metrics.size
    }

    fun updateDataset(newMetrics: List<Metric>) {
        metrics = newMetrics
        notifyDataSetChanged()
    }
}
