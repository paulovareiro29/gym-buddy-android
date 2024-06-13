package ipvc.gymbuddy.app.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ipvc.gymbuddy.app.fragments.trainer.client.userPlan.TrainerClientUserPlanFragment
import ipvc.gymbuddy.app.fragments.trainer.client.metrics.TrainerClientMetricsFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val userId: String
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrainerClientUserPlanFragment.newInstance(userId)
            1 -> TrainerClientMetricsFragment.newInstance(userId)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
