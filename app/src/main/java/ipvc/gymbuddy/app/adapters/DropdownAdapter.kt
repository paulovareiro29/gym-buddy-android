package ipvc.gymbuddy.app.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.models.DropdownItem

class DropdownAdapter(context: Context, dropdown: AutoCompleteTextView, private val items: List<DropdownItem>)
    : ArrayAdapter<DropdownItem>(context, R.layout.dropdown_item, items) {

        var selected: DropdownItem? = null

    init {
        dropdown.setOnItemClickListener { _, _, position, _ ->
            selected = getItem(position)
        }
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): DropdownItem {
        return items[position]
    }
}