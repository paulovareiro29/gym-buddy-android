package ipvc.gymbuddy.app.fragments.trainer.contracts

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ContractAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerListClientsOverviewBinding
import ipvc.gymbuddy.app.viewmodels.trainer.contract.TrainerListClientsOverviewViewModel

class TrainerListClientsOverviewFragment : BaseFragment<FragmentTrainerListClientsOverviewBinding>(
    FragmentTrainerListClientsOverviewBinding::inflate) {

    private lateinit var viewModel: TrainerListClientsOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.clients_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getContracts()
        viewModel.contractsData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = ContractAdapter(it.data)
            }
        }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }

    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.contractsData.value?.data?.filter {
            it.beneficiary.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = ContractAdapter(filtered)
    }
}