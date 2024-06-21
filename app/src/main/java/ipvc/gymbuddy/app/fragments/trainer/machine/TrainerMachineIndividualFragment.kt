package ipvc.gymbuddy.app.fragments.trainer.machine

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.CategoryAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentTrainerMachineIndividualBinding
import ipvc.gymbuddy.app.utils.ImageUtils

class TrainerMachineIndividualFragment : BaseFragment<FragmentTrainerMachineIndividualBinding>(
    FragmentTrainerMachineIndividualBinding::inflate) {

    private lateinit var categoriesRecyclerView: RecyclerView
    private var data: Machine? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = Gson().fromJson(it.getString("data"), Machine::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (data == null) {
            navController.navigateUp()
            return
        }

        loadToolbar(data!!.name)

        binding.name.text = data!!.name

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW)
        categoriesRecyclerView.adapter = CategoryAdapter(data!!.categories, CategoryAdapter.Direction.ROW)

        Glide.with(requireContext())
            .load(ImageUtils.convertBase64ToBitmap(data!!.photo) ?: data!!.photo)
            .placeholder(R.drawable.no_image)
            .into(binding.photo)
    }
}