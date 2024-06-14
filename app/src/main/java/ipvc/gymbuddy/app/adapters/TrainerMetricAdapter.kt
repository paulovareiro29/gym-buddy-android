package ipvc.gymbuddy.app.adapters

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.fragments.trainer.client.metrics.TrainerClientMetricsUpdateModal
import ipvc.gymbuddy.app.utils.DateUtils


class TrainerMetricAdapter(private val fragmentManager: FragmentManager, private var dataset: List<Metric>) : BaseRecyclerAdapter<Metric, TrainerMetricAdapter.ViewHolder>(dataset) {

    private var onMetricDeleteListener: ((Metric) -> Unit)? = null

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val date: TextView = view.findViewById(R.id.date)
        val name: TextView = view.findViewById(R.id.name)
        val creator: TextView = view.findViewById(R.id.creator)
        val value: TextView = view.findViewById(R.id.value)
        val editButton: ImageButton = view.findViewById(R.id.edit_metric)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_metric)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_trainer_metrics
    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Metric, position: Int) {
        holder.name.text = item.type?.name
        holder.creator.text = holder.itemView.context.getString(R.string.submitted_by, item.creator.name)
        holder.value.text = item.value
        holder.editButton.setOnClickListener {handleEdit(item) }
        holder.deleteButton.setOnClickListener { handleDelete(item) }

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

    private fun handleDelete(item: Metric) {
        onMetricDeleteListener?.invoke(item)
    }

    private fun handleEdit(item: Metric) {
        val dialogFragment = TrainerClientMetricsUpdateModal().apply {
            arguments = Bundle().apply {
                putString("metric", Gson().toJson(item))
            }
        }
        dialogFragment.show(fragmentManager, "TrainerClientMetricUpdateModal")
    }

    fun setOnMetricDeleteListener(listener: (Metric) -> Unit) {
        onMetricDeleteListener = listener
    }
}
