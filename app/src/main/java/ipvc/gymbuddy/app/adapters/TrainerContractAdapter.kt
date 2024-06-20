package ipvc.gymbuddy.app.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Contract
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.Locale

class TrainerContractAdapter(dataset: List<Contract>): BaseRecyclerAdapter<Contract, TrainerContractAdapter.ViewHolder>(dataset) {

    private val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.name)
        val email: TextView = view.findViewById(R.id.email)
        val contractEndDate: TextView = view.findViewById(R.id.contract_endDate)
        val viewClient : ImageView = view.findViewById(R.id.view_client)
    }

    override fun getItemLayout(): Int = R.layout.recycle_trainer_adapter_contract

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: ViewHolder, item: Contract, position: Int) {
        val endDate = originalFormat.parse(item.end_date)
        val formattedEndDate = endDate?.let { targetFormat.format(it) }

        holder.name.text = item.beneficiary.name
        holder.email.text = item.beneficiary.email
        holder.contractEndDate.text = holder.itemView.context.getString(R.string.contract_end_date, formattedEndDate)
        holder.viewClient.setOnClickListener {
            val contractJson = Gson().toJson(item)
            holder.itemView.findNavController().navigate(
                R.id.trainer_client_overview_fragment,
                bundleOf("contract" to contractJson)
            )
        }
    }
}