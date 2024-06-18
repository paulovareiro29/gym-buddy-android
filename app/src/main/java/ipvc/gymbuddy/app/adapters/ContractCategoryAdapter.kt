package ipvc.gymbuddy.app.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import ipvc.gymbuddy.api.models.ContractCategory
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class ContractCategoryAdapter(dataset: List<ContractCategory>, val direction: Direction = Direction.COLUMN) : BaseRecyclerAdapter<ContractCategory, ContractCategoryAdapter.ViewHolder>(dataset) {

    private var onDeleteListener: ((ContractCategory) -> Unit)? = null

    enum class Direction {
        ROW,
        COLUMN
    }

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val parent: LinearLayout = view.findViewById(R.id.adapter_parent)
        val name: TextView = view.findViewById(R.id.name)
        val delete: ImageButton = view.findViewById(R.id.delete)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_admin_contract_category

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: ContractCategory, position: Int) {
        holder.name.text = item.name
        holder.delete.setOnClickListener { onDeleteListener?.let { listener -> listener(item) } }

        val layoutParams = holder.parent.layoutParams as ViewGroup.MarginLayoutParams
        if (direction == Direction.ROW) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams.marginEnd = (8 * holder.parent.context.resources.displayMetrics.density).toInt()
        }
    }

    fun setOnDeleteListener(listener: (ContractCategory) -> Unit) {
        onDeleteListener = listener
    }
}
