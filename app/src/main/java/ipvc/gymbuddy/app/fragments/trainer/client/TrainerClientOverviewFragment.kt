package ipvc.gymbuddy.app.fragments.trainer.client

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Contract
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ViewPagerAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerClientOverviewBinding
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientMetricOverviewViewModel
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientPlanViewModel

class TrainerClientOverviewFragment : BaseFragment<FragmentTrainerClientOverviewBinding>(
    FragmentTrainerClientOverviewBinding::inflate
) {

    private lateinit var metricViewModel: TrainerClientMetricOverviewViewModel
    private lateinit var planViewModel: TrainerClientPlanViewModel
    private var contract: Contract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        metricViewModel = getViewModel()
        planViewModel = getViewModel()
        arguments?.let {
            contract = Gson().fromJson(it.getString("contract"), Contract::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (contract == null) {
            navController.navigateUp()
            return
        }

        loadToolbar(contract!!.beneficiary.name)
        binding.clientName.text = contract!!.beneficiary.name

        val userId = contract!!.beneficiary.id

        val adapter = ViewPagerAdapter(this, userId)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.training_plans)
                1 -> getString(R.string.metrics)
                else -> null
            }
        }.attach()
    }
}
