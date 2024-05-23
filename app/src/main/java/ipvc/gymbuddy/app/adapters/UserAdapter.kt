package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder

class UserAdapter(dataset: List<User>) : BaseRecyclerAdapter<User, UserAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_user

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: User) {
        holder.textView.text = item.email
    }
}
