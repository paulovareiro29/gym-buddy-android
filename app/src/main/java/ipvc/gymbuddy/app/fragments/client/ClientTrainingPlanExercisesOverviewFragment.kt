package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ClientTrainingPlanExerciseAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentClientTrainingPlanExercisesOverviewBinding
import ipvc.gymbuddy.app.fragments.ui.TabRecyclerViewFragment
import ipvc.gymbuddy.app.viewmodels.trainer.planExercise.TrainerTrainingPlanExerciseOverviewModel

class ClientTrainingPlanExercisesOverviewFragment : BaseFragment<FragmentClientTrainingPlanExercisesOverviewBinding>(
    FragmentClientTrainingPlanExercisesOverviewBinding::inflate
) {

    private lateinit var viewModel: TrainerTrainingPlanExerciseOverviewModel
    private var trainingPlan: TrainingPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        arguments?.let {
            trainingPlan = Gson().fromJson(it.getString("trainingPlan"), TrainingPlan::class.java)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.client_user_plan_overview_fragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (trainingPlan == null) {
            navController.navigateUp()
            return
        }

        loadToolbar(trainingPlan!!.name)

        viewModel.getPlanExercises(trainingPlan!!.id)

        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        viewModel.planExerciseData.observe(viewLifecycleOwner) { resource ->
            val exercises = resource.data ?: return@observe
            val uniqueDays = exercises.map { it.day }.distinct()

            val fragments = uniqueDays.map { day ->
                val dayExercises = exercises.filter { it.day == day }
                val adapter = ClientTrainingPlanExerciseAdapter(dayExercises)
                TabRecyclerViewFragment(adapter)
            }

            viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount() = fragments.size
                override fun createFragment(position: Int) = fragments[position]
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = uniqueDays[position]
            }.attach()
        }
    }
}
