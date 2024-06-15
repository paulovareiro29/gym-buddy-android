package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.ImageUtils

class UserAdapter(dataset: List<User>) : BaseRecyclerAdapter<User, UserAdapter.ViewHolder>(dataset) {

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.photo)
        val name: TextView = view.findViewById(R.id.name)
        val email: TextView = view.findViewById(R.id.email)
        val view: ImageButton = view.findViewById(R.id.view)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_user

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: User, position: Int) {
        val bitmap = item.avatar?.let { ImageUtils.convertBase64ToBitmap(it) }
        if (bitmap != null) {
            holder.photo.setImageBitmap(bitmap)
        }

        holder.name.text = item.name
        holder.email.text = item.email
        holder.view.setOnClickListener { handleView(holder, item) }
    }

    private fun handleView(holder: ViewHolder, item: User) {
        holder.itemView.findNavController().navigate(
            R.id.admin_user_individual_fragment,
            bundleOf("data" to Gson().toJson(item))
        )
    }
}
