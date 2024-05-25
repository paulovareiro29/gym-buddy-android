package ipvc.gymbuddy.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R

class TrainingPlanAdapter : RecyclerView.Adapter<TrainingPlanAdapter.ViewHolder>() {

    private var trainingPlans: List<TrainingPlan> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_training_plan, parent, false)
        return ViewHolder(view)
    }
    // adicionar mais informações
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTrainingPlan = trainingPlans[position]
        holder.textViewPlanName.text = currentTrainingPlan.name

    }

    override fun getItemCount(): Int {
        return trainingPlans.size
    }

    fun setTrainingPlans(trainingPlans: List<TrainingPlan>) {
        this.trainingPlans = trainingPlans
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewPlanName: TextView = itemView.findViewById(R.id.plan_name)
    }
}
