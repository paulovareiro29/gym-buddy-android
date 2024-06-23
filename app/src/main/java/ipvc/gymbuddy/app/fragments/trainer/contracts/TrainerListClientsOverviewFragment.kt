package ipvc.gymbuddy.app.fragments.trainer.contracts

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainerContractAdapter
import ipvc.gymbuddy.app.core.AsyncData
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
            when (it.status) {
                AsyncData.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    recyclerView.adapter = TrainerContractAdapter(it.data ?: listOf())
                }
            }
        }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.contractsData.value?.data?.filter {
            it.beneficiary.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = TrainerContractAdapter(filtered)
    }
}