package ipvc.gymbuddy.app.adapters

import android.view.View
import com.google.android.material.chip.Chip
import ipvc.gymbuddy.api.models.Category
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class AddCategoryAdapter(dataset: List<Category>) : BaseRecyclerAdapter<Category, AddCategoryAdapter.ViewHolder>(dataset) {

    val selected: MutableList<Category> = mutableListOf()
    class ViewHolder(view: View) : BaseViewHolder(view) {
        val category: Chip = view.findViewById(R.id.add_category)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_add_category

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Category) {
        holder.category.text = item.name
        updateViewHolder(holder, item)

        holder.category.setOnClickListener {
            if (selected.contains(item)) {
                selected.remove(item)
            } else {
                selected.add(item)
            }

            updateViewHolder(holder, item)
        }
    }

    private fun updateViewHolder(holder: ViewHolder, item: Category) {
        if (selected.contains(item)) {
            holder.category.setChipIconResource(R.drawable.baseline_remove_24)
            holder.category.setChipBackgroundColorResource(R.color.primaryLightColor)
            holder.category.setChipIconTintResource(R.color.white)
            holder.category.setTextColor(parent.context.getColor(R.color.white))
        } else {
            holder.category.setChipIconResource(R.drawable.baseline_add_24)
            holder.category.setChipBackgroundColorResource(R.color.white)
            holder.category.setChipIconTintResource(R.color.textPrimary)
            holder.category.setTextColor(parent.context.getColor(R.color.textPrimary))
        }
    }
}
