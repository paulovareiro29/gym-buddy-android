package ipvc.gymbuddy.app.fragments.admin.exercise

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Exercise
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.CategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminExerciseIndividualBinding
import ipvc.gymbuddy.app.utils.ImageUtils

class AdminExerciseIndividualFragment : BaseFragment<FragmentAdminExerciseIndividualBinding>(
    FragmentAdminExerciseIndividualBinding::inflate) {

    private lateinit var categoriesRecyclerView: RecyclerView
    private var data: Exercise? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = Gson().fromJson(it.getString("data"), Exercise::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (data == null) {
            navController.navigateUp()
            return
        }

        loadToolbar(data!!.name)

        binding.exerciseName.text = data!!.name
        binding.machineName.text = data!!.machine.name

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW)
        categoriesRecyclerView.adapter = CategoryAdapter(data!!.categories, CategoryAdapter.Direction.ROW)

        if (data!!.photo != null){
            Glide.with(requireContext())
                .load(ImageUtils.convertBase64ToBitmap(data!!.photo!!) ?: data!!.photo)
                .placeholder(R.drawable.no_image)
                .into(binding.exerciseImage)
        }

        Glide.with(requireContext())
            .load(ImageUtils.convertBase64ToBitmap(data!!.machine.photo) ?: data!!.machine.photo)
            .placeholder(R.drawable.no_image)
            .into(binding.machineImage)
    }
}