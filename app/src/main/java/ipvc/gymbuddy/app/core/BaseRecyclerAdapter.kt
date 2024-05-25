package ipvc.gymbuddy.app.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, VH : BaseViewHolder>(private var dataset: List<T>) :
    RecyclerView.Adapter<VH>() {

    override fun getItemCount() = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false)
        return createViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolder(holder, dataset[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(updated: List<T>) {
        dataset = updated
        notifyDataSetChanged()
    }

    protected abstract fun getItemLayout(): Int
    protected abstract fun createViewHolder(view: View): VH
    protected abstract fun bindViewHolder(holder: VH, item: T)
}