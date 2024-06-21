package ipvc.gymbuddy.app.adapters

import Modal
import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.utils.StringUtils

class TrainingPlanAdapter(dataset: List<TrainingPlan>): BaseRecyclerAdapter<TrainingPlan, TrainingPlanAdapter.ViewHolder>(dataset) {

    private var onTrainingPlanDeleteListener: ((TrainingPlan) -> Unit)? = null

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.plan_name)
        val editButton: ImageButton = view.findViewById(R.id.edit_plan)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_plan)
        val addClientButton: ImageButton = view.findViewById(R.id.add_client)
        val clients: TextView = view.findViewById(R.id.linked_clients)
        val viewPlan: ImageButton = view.findViewById(R.id.view_plan)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_training_plan

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: TrainingPlan, position: Int) {
        holder.name.text = StringUtils.capitalize(item.name)
        holder.editButton.setOnClickListener {
            holder.itemView
                .findNavController()
                .navigate(
                    R.id.trainer_trainingplans_update_fragment,
                    bundleOf("trainingPlanId" to item.id)
                )
        }

        if (item.clients.isNotEmpty()) {
            holder.clients.visibility = View.VISIBLE
            @SuppressLint("SetTextI18n")
            holder.clients.text = "${holder.itemView.context.getString(R.string.associated_clients)}:${item.clients.joinToString(",") { "\n - ${it.email}" }}"
        }

        holder.addClientButton.setOnClickListener {
            if (NetworkUtils.isOffline(holder.itemView.context)) {
                holder.itemView.findNavController().navigate(R.id.trainer_offline_fragment)
                return@setOnClickListener
            }
            val activity = it.context as FragmentActivity
            val title = activity.getString(R.string.add_client_to, item.name)
            val bundle = bundleOf("trainingPlan" to Gson().toJson(item))
            val dialogFragment = Modal.newInstance(title, "TrainerAddClientToPlanModal", bundle)
            dialogFragment.show(activity.supportFragmentManager, "TrainerAddClientToPlanModal")
        }

        holder.deleteButton.setOnClickListener {
            onTrainingPlanDeleteListener?.invoke(item)
        }

        holder.viewPlan.setOnClickListener {
            val trainingPlanJson = Gson().toJson(item)
            holder.itemView.findNavController().navigate(
                R.id.trainer_trainingplan_exercises_overview_fragment,
                bundleOf("trainingPlan" to trainingPlanJson)
            )
        }
    }

    fun setOnTrainingPlanDeleteListener(listener: (TrainingPlan) -> Unit) {
        onTrainingPlanDeleteListener = listener
    }
}