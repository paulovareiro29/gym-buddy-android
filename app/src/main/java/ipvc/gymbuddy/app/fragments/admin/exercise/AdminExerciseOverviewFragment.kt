package ipvc.gymbuddy.app.fragments.admin.exercise

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ExerciseAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminExerciseOverviewBinding
import ipvc.gymbuddy.app.viewmodels.admin.exercise.AdminExerciseOverviewViewModel

class AdminExerciseOverviewFragment : BaseFragment<FragmentAdminExerciseOverviewBinding>(
    FragmentAdminExerciseOverviewBinding::inflate
) {
    private lateinit var viewModel: AdminExerciseOverviewViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.exercises_overview))

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getExercises()
        viewModel.exercisesData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                recyclerView.adapter = ExerciseAdapter(it.data)
            }
        }

        binding.createExercise.setOnClickListener { navController.navigate(R.id.admin_exercise_create_fragment) }
        binding.searchInput.editText?.addTextChangedListener { handleSearch(it.toString()) }
    }

    private fun handleSearch(search: String) {
        val filtered = viewModel.exercisesData.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        recyclerView.adapter = ExerciseAdapter(filtered)
    }
}
