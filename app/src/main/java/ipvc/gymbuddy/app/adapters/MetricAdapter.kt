package ipvc.gymbuddy.app.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.fragments.trainer.client.metrics.TrainerClientMetricsUpdateModal

class MetricAdapter(private val fragmentManager: FragmentManager, private var metrics: List<Metric>) : RecyclerView.Adapter<MetricAdapter.ViewHolder>() {

    private var onMetricDeleteListener: ((Metric) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val creator: TextView = itemView.findViewById(R.id.creator)
        val value: TextView = itemView.findViewById(R.id.value)
        val editButton: ImageButton = itemView.findViewById(R.id.edit_metric)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_metric)
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

        holder.editButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("metric", Gson().toJson(item))
            }
            val dialogFragment = TrainerClientMetricsUpdateModal().apply {
                arguments = bundle
            }
            dialogFragment.show(fragmentManager, "TrainerClientMetricUpdateModal")
        }

        holder.deleteButton.setOnClickListener {
            onMetricDeleteListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return metrics.size
    }

    fun updateDataset(newMetrics: List<Metric>) {
        metrics = newMetrics
        notifyDataSetChanged()
    }

    fun setOnMetricDeleteListener(listener: (Metric) -> Unit) {
        onMetricDeleteListener = listener
    }
}
