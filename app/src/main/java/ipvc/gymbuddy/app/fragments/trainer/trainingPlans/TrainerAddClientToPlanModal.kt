
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.DropdownAdapter
import ipvc.gymbuddy.app.models.DropdownItem
import ipvc.gymbuddy.app.viewmodels.trainer.contract.TrainerListClientsOverviewViewModel

class TrainerAddClientToPlanModal : Modal(R.layout.fragment_trainer_add_client_to_plan_modal) {

    private lateinit var viewModel: TrainerListClientsOverviewViewModel

    private var clientAutoCompleteTextView: AutoCompleteTextView? = null
    private var startDateEditText: EditText? = null
    private var endDateEditText: EditText? = null
    private var titleEditText: TextView? = null
    private var submitButton: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TrainerListClientsOverviewViewModel::class.java]
        clientAutoCompleteTextView = view.findViewById(R.id.client)
        startDateEditText = view.findViewById(R.id.edit_text_start_date)
        endDateEditText = view.findViewById(R.id.edit_text_end_date)
        titleEditText = view.findViewById(R.id.modal_title)
        submitButton = view.findViewById(R.id.submit_button)

        loadClients()
    }

    private fun loadClients() {
        viewModel.getContracts()

        viewModel.contractsData.observe(viewLifecycleOwner) { contracts ->
            if (contracts.data != null) {
                val adapter = DropdownAdapter(
                    requireContext(),
                    clientAutoCompleteTextView!!,
                    contracts.data.map { contract ->
                        DropdownItem(contract.beneficiary.id, contract.beneficiary.name)
                    }
                )
                clientAutoCompleteTextView?.setAdapter(adapter)
            }
        }
    }
}
