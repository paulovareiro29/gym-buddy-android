package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Exercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.ImageUtils

class ExerciseAdapter(dataset: List<Exercise>) : BaseRecyclerAdapter<Exercise, ExerciseAdapter.ViewHolder>(dataset) {

    private var onDeleteListener: ((Exercise) -> Unit)? = null

    class ViewHolder(view: View) : BaseViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.photo)
        val name: TextView = view.findViewById(R.id.name)
        val categories: TextView = view.findViewById(R.id.categories)
        val view: ImageButton = view.findViewById(R.id.view)
        val edit: ImageButton = view.findViewById(R.id.edit)
        val delete: ImageButton = view.findViewById(R.id.delete)
    }

    override fun getItemLayout(): Int = R.layout.recycler_adapter_machine

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Exercise, position: Int) {
        if (item.photo != null) {
            val bitmap = ImageUtils.convertBase64ToBitmap(item.photo!!)
            if (bitmap != null) {
                holder.photo.setImageBitmap(bitmap)
            }
        }
        holder.name.text = item.name
        holder.categories.text = item.categories.joinToString(", ") { it.name }

        holder.view.setOnClickListener { handleViewExercise(holder, item) }
        holder.edit.setOnClickListener { handleEdit(holder, item) }
        holder.delete.setOnClickListener { onDeleteListener?.let { listener -> listener(item) } }
    }

    fun setOnDeleteListener(listener: (Exercise) -> Unit) {
        onDeleteListener = listener
    }

    private fun handleViewExercise(holder: ViewHolder, item: Exercise) {
        holder.itemView.findNavController().navigate(
            R.id.admin_exercise_individual_fragment,
            bundleOf("data" to Gson().toJson(item))
        )
    }

    private fun handleEdit(holder: ViewHolder, item: Exercise) {
        holder.itemView
            .findNavController()
            .navigate(
                R.id.admin_exercise_update_fragment,
                bundleOf("data" to Gson().toJson(item))
            )
    }
}
