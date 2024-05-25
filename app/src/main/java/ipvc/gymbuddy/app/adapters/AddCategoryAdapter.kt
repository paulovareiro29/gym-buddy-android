package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.Button
import android.widget.TextView
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class AddCategoryAdapter(dataset: List<Category>) : BaseRecyclerAdapter<Category, AddCategoryAdapter.ViewHolder>(dataset) {

    val selected: MutableList<Category> = mutableListOf()
    class ViewHolder(view: View) : BaseViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val add: Button = view.findViewById(R.id.add)
        val remove: Button = view.findViewById(R.id.remove)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_add_category

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Category) {
        holder.name.text = item.name
        val isAdded = selected.contains(item)
        updateButtonVisibility(holder, isAdded)

        holder.add.setOnClickListener {
            selected.add(item)
            updateButtonVisibility(holder, true)
        }

        holder.remove.setOnClickListener {
            selected.remove(item)
            updateButtonVisibility(holder, false)
        }
    }

    private fun updateButtonVisibility(holder: ViewHolder, isAdded: Boolean) {
        holder.add.visibility = if (isAdded) View.GONE else View.VISIBLE
        holder.remove.visibility = if (isAdded) View.VISIBLE else View.GONE
    }
}
