package ipvc.gymbuddy.app.fragments.trainer.client.metrics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.MetricAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerClientMetricsBinding
import ipvc.gymbuddy.app.fragments.trainer.client.userPlan.TrainerClientUserPlanFragment
import ipvc.gymbuddy.app.viewmodels.client.ClientMetricOverViewModel

class TrainerClientMetricsFragment : BaseFragment<FragmentTrainerClientMetricsBinding>(
    FragmentTrainerClientMetricsBinding::inflate) {

    private lateinit var viewModel: ClientMetricOverViewModel
    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val ARG_USER_ID = "userId"

        fun newInstance(userId: String): TrainerClientMetricsFragment {
            val fragment = TrainerClientMetricsFragment()
            val args = Bundle()
            args.putString(ARG_USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val userId = arguments?.getString(TrainerClientMetricsFragment.ARG_USER_ID) ?: return

        viewModel.getMetrics(userId)
        viewModel.metricsData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = MetricAdapter(it.data)
            }
        }

    }
}

