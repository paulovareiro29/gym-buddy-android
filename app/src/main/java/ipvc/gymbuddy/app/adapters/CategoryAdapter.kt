package ipvc.gymbuddy.app.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class CategoryAdapter(dataset: List<Category>, val direction: Direction = Direction.COLUMN) : BaseRecyclerAdapter<Category, CategoryAdapter.ViewHolder>(dataset) {

    enum class Direction {
        ROW,
        COLUMN
    }

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val parent = view.findViewById<LinearLayout>(R.id.adapter_parent)
        val name: TextView = view.findViewById(R.id.name)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_category

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Category) {
        holder.name.text = item.name

        val layoutParams = holder.parent.layoutParams as ViewGroup.MarginLayoutParams

        if (direction == Direction.ROW) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
           layoutParams.marginEnd = (8 * holder.parent.context.resources.displayMetrics.density).toInt()
        }
    }
}
