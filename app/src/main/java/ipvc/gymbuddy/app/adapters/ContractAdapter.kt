package ipvc.gymbuddy.app.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import ipvc.gymbuddy.api.models.Contract
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseRecyclerAdapter
import ipvc.gymbuddy.app.core.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.Locale

class ContractAdapter(dataset: List<Contract>): BaseRecyclerAdapter<Contract, ContractAdapter.ViewHolder>(dataset) {

    private val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    class ViewHolder(view: View) : BaseViewHolder(view){
        val name: TextView = view.findViewById(R.id.name)
        val email: TextView = view.findViewById(R.id.email)
        val contractEndDate: TextView = view.findViewById(R.id.contract_endDate)
    }

    override fun getItemLayout(): Int = R.layout.recycle_adapter_contract

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, item: Contract) {
        val endDate = originalFormat.parse(item.end_date)
        val formattedEndDate = endDate?.let { targetFormat.format(it) }

        holder.name.text = item.beneficiary.name
        holder.email.text = item.beneficiary.email
        holder.contractEndDate.text = "Contract ends on $formattedEndDate"

    }
}