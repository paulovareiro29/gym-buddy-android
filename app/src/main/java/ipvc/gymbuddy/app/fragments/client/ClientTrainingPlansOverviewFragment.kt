package ipvc.gymbuddy.app.fragments.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ipvc.gymbuddy.app.adapters.ClientUserPlanAdapter
import ipvc.gymbuddy.app.databinding.FragmentClientUserPlanOverviewBinding
import ipvc.gymbuddy.app.viewmodels.client.TrainerClientPlanViewModel

class ClientTrainingPlansOverviewFragment : Fragment() {

    private var _binding: FragmentClientUserPlanOverviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrainerClientPlanViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentClientUserPlanOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TrainerClientPlanViewModel::class.java]

        val userId = arguments?.getString("userId") ?: throw IllegalStateException("User ID must be passed to ClientTrainingPlansOverviewFragment")

        viewModel.getUserPlans(userId)
        viewModel.userPlans.observe(viewLifecycleOwner) { asyncData ->
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = ClientUserPlanAdapter(asyncData.data ?: emptyList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
