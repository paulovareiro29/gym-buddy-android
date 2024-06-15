package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class AdminCategoryAdapter(dataset: List<Category>) : BaseRecyclerAdapter<Category, AdminCategoryAdapter.ViewHolder>(dataset) {

    private var onDeleteListener: ((Category) -> Unit)? = null

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val delete: ImageButton = view.findViewById(R.id.delete)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_admin_category

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Category, position: Int) {
        holder.name.text = item.name
        holder.delete.setOnClickListener { onDeleteListener?.let { listener -> listener(item) } }
    }

    fun setOnDeleteListener(listener: (Category) -> Unit) {
        onDeleteListener = listener
    }
}
