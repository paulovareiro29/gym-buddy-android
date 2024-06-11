package ipvc.gymbuddy.app.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ipvc.gymbuddy.api.models.Metric
import ipvc.gymbuddy.api.models.UserPlan
import ipvc.gymbuddy.app.fragments.ui.TabRecyclerViewFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val userPlans: List<UserPlan>,
    private val metrics: List<Metric>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabRecyclerViewFragment(UserPlanAdapter(userPlans))
            1 -> TabRecyclerViewFragment(MetricAdapter(metrics))
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
