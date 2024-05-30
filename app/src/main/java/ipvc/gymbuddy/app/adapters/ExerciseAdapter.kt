package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.Exercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class ExerciseAdapter(dataset: List<Exercise>) : BaseRecyclerAdapter<Exercise, ExerciseAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val categories: TextView = view.findViewById(R.id.categories)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_machine

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Exercise) {
        holder.name.text = item.name
        holder.categories.text = item.categories.joinToString(", ") { it.name }
    }
}