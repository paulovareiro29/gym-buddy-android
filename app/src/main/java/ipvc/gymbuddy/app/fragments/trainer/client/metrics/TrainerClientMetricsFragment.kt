package ipvc.gymbuddy.app.fragments.trainer.client.metrics

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageButton
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ipvc.gymbuddy.app.adapters.MetricAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerClientMetricsBinding
import ipvc.gymbuddy.app.fragments.ui.TabRecyclerViewFragment
import ipvc.gymbuddy.app.viewmodels.client.ClientMetricOverViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrainerClientMetricsFragment : BaseFragment<FragmentTrainerClientMetricsBinding>(
    FragmentTrainerClientMetricsBinding::inflate) {

    private lateinit var viewModel: ClientMetricOverViewModel
    private var currentTabIndex: Int = 0
    private lateinit var visibleDays: List<Date>
    private lateinit var tabLayout: TabLayout

    companion object {
        private const val ARG_USER_ID = "userId"
        private const val MAX_VISIBLE_TABS = 2

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
        val userId = arguments?.getString(ARG_USER_ID) ?: return

        viewModel.getMetrics(userId)

        val viewPager: ViewPager2 = binding.viewPager
        tabLayout = binding.tabLayout
        val prevButton: ImageButton = binding.prevButton
        val nextButton: ImageButton = binding.nextButton

        viewModel.metricsData.observe(viewLifecycleOwner) { resource ->
            val metrics = resource.data ?: return@observe
            val uniqueDays = metrics.map { it.date }.distinct().sortedBy { it.time }
            visibleDays = uniqueDays


            updateTabs()

            viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount() = visibleDays.size
                override fun createFragment(position: Int) =
                    TabRecyclerViewFragment(MetricAdapter(metrics.filter { it.date == visibleDays[position] }))
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(visibleDays[position])
                tab.text = formattedDate
            }.attach()

            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

            prevButton.setOnClickListener {
                if (currentTabIndex > 0) {
                    currentTabIndex--
                    viewPager.setCurrentItem(currentTabIndex, true)
                    updateTabs()
                }
            }

            nextButton.setOnClickListener {
                if (currentTabIndex < visibleDays.size - 1) {
                    currentTabIndex++
                    viewPager.setCurrentItem(currentTabIndex, true)
                    updateTabs()
                }
            }
        }
    }

    private fun updateTabs() {
        val startIndex = if (currentTabIndex - MAX_VISIBLE_TABS / 2 < 0) 0 else currentTabIndex - MAX_VISIBLE_TABS / 2
        val endIndex = if (startIndex + MAX_VISIBLE_TABS >= visibleDays.size) visibleDays.size - 1 else startIndex + MAX_VISIBLE_TABS - 1
        val newVisibleDays = visibleDays.subList(startIndex, endIndex + 1)
        tabLayout.removeAllTabs()
        newVisibleDays.forEachIndexed { index, date ->
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
            val tabText = if (index == currentTabIndex - startIndex) {
                SpannableString(formattedDate).apply { setSpan(StyleSpan(Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
            } else {
                formattedDate
            }
            tabLayout.addTab(tabLayout.newTab().setText(tabText))
        }
        tabLayout.selectTab(tabLayout.getTabAt(currentTabIndex - startIndex))
    }

}
