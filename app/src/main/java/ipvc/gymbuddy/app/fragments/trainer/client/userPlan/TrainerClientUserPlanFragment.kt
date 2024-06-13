package ipvc.gymbuddy.app.fragments.trainer.client.userPlan

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.UserPlanAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerClientUserPlanBinding
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientPlanViewModel

class TrainerClientUserPlanFragment : BaseFragment<FragmentTrainerClientUserPlanBinding>(
    FragmentTrainerClientUserPlanBinding::inflate
) {

    private lateinit var viewModel: TrainerClientPlanViewModel
    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val ARG_USER_ID = "userId"

        fun newInstance(userId: String): TrainerClientUserPlanFragment {
            val fragment = TrainerClientUserPlanFragment()
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

        val userId = arguments?.getString(ARG_USER_ID) ?: return

        viewModel.getUserPlans(userId)
        viewModel.userPlans.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = UserPlanAdapter(it.data)
            }
        }
    }
}
