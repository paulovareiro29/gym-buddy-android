package ipvc.gymbuddy.app.fragments.trainer.client.metrics

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.MetricAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerClientMetricsBinding
import ipvc.gymbuddy.app.fragments.ui.TabRecyclerViewFragment
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientMetricOverviewViewModel
import ipvc.gymbuddy.app.viewmodels.trainer.metrics.TrainerClientMetricDeleteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrainerClientMetricsFragment : BaseFragment<FragmentTrainerClientMetricsBinding>(
    FragmentTrainerClientMetricsBinding::inflate), TrainerClientMetricsCreateModal.MetricCreationListener {

    private lateinit var viewModel: TrainerClientMetricOverviewViewModel
    private lateinit var deleteViewModel: TrainerClientMetricDeleteViewModel
    private var currentTabIndex: Int = 0
    private lateinit var visibleDays: List<Date>
    private lateinit var tabLayout: TabLayout

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
        deleteViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getString(ARG_USER_ID) ?: return

        viewModel.getMetrics(userId)

        val viewPager: ViewPager2 = binding.viewPager
        val prevButton: ImageButton = binding.prevButton
        val nextButton: ImageButton = binding.nextButton
        val createMetrics: Button = binding.createMetric

        tabLayout = binding.tabLayout

        createMetrics.setOnClickListener {
            val bundle = Bundle().apply {
                putString("clientId", userId)
            }
            val dialogFragment = TrainerClientMetricsCreateModal().apply {
                arguments = bundle
            }
            dialogFragment.setMetricCreationListener(this@TrainerClientMetricsFragment)
            dialogFragment.show(childFragmentManager, "TrainerTrainingPlanExerciseCreateModal")
        }

        viewModel.metricsData.observe(viewLifecycleOwner) { resource ->
            val metrics = resource.data ?: return@observe
            val uniqueDays = metrics.map { it.date }.distinct().sortedBy { it.time }
            visibleDays = uniqueDays

            viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount() = visibleDays.size
                override fun createFragment(position: Int) =
                    TabRecyclerViewFragment(MetricAdapter(childFragmentManager, metrics.filter { it.date == visibleDays[position] }).apply {
                        setOnMetricDeleteListener { metric -> showDeleteConfirmationDialog(metric) }
                    })
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(visibleDays[position])
                tab.text = formattedDate
            }.attach()

            prevButton.setOnClickListener {
                if (currentTabIndex > 0) {
                    currentTabIndex--
                    viewPager.setCurrentItem(currentTabIndex, true)
                }
            }

            nextButton.setOnClickListener {
                if (currentTabIndex < visibleDays.size - 1) {
                    currentTabIndex++
                    viewPager.setCurrentItem(currentTabIndex, true)
                }
            }
        }

    }

    override fun onMetricCreated() {
        val userId = arguments?.getString(ARG_USER_ID) ?: return
        viewModel.getMetrics(userId)
    }

    private fun showDeleteConfirmationDialog(metric: Metric) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.confirm_delete))
            setMessage(getString(R.string.delete_message, metric.type!!.name))
            setPositiveButton(getString(R.string.delete)) { _, _ ->
                deleteViewModel.deleteMetric(metric.id)
                val userId = arguments?.getString(ARG_USER_ID)
                userId?.let {
                    viewModel.getMetrics(it)
                }
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}
