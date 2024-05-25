package ipvc.gymbuddy.app.fragments.trainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.TrainingPlanAdapter
import ipvc.gymbuddy.app.viewmodels.TrainingPlanViewModel

class TrainingPlansFragment : Fragment() {

    private lateinit var trainingPlanViewModel: TrainingPlanViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrainingPlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_trainer_training_plans, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        adapter = TrainingPlanAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingPlanViewModel = ViewModelProvider(this).get(TrainingPlanViewModel::class.java)
        trainingPlanViewModel.trainingPlans.observe(viewLifecycleOwner, Observer { trainingPlans ->
            adapter.setTrainingPlans(trainingPlans)
        })
        trainingPlanViewModel.getTrainingPlans()
    }
}
