package ipvc.gymbuddy.app.fragments.admin.machine

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ipvc.gymbuddy.api.models.Machine
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.AddCategoryAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentAdminMachineCreateBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.viewmodels.admin.machine.AdminMachineUpdateViewModel
import java.io.IOException

class AdminMachineUpdateFragment : BaseFragment<FragmentAdminMachineCreateBinding>(
    FragmentAdminMachineCreateBinding::inflate
) {
    private lateinit var viewModel: AdminMachineUpdateViewModel
    private lateinit var gallery: ActivityResultLauncher<PickVisualMediaRequest>

    private var selectedPhoto: String? = null

    private lateinit var machine: Machine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        gallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { handleUploadPhoto(it) }

        try {
            machine = Gson().fromJson(arguments?.getString("data"), Machine::class.java)
        } catch (_: JsonSyntaxException) {
            navController.navigateUp()
        }

        if (NetworkUtils.isOffline(requireContext())) {
            replaceFragmentBy(R.id.admin_offline_fragment)
            return
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.update_machine))

        resetView()
        loadCategories()

        binding.categoryRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.submit.setOnClickListener { handleSubmit() }
        binding.upload.setOnClickListener { launchPhotoGallery() }
        binding.removePhoto.setOnClickListener { handleRemovePhoto() }
        binding.searchCategoryInput.editText?.addTextChangedListener { handleSearchCategory(it.toString()) }
        viewModel.updateData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.IDLE -> resetView()
                AsyncData.Status.LOADING -> {
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
                AsyncData.Status.SUCCESS -> {
                    resetView()
                    binding.message.text = getString(R.string.created_successfully)
                    binding.message.setTextColor(requireActivity().getColor(R.color.secondaryDarkColor))
                    binding.message.visibility = View.VISIBLE
                }
                AsyncData.Status.ERROR -> {
                    binding.message.text = getString(R.string.something_went_wrong)
                    binding.message.setTextColor(requireActivity().getColor(R.color.error))
                    binding.message.visibility = View.VISIBLE
                    binding.submit.isEnabled = false
                    binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryLightColor))
                }
            }
        }
    }

    private fun launchPhotoGallery() {
        gallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun handleUploadPhoto(uri: Uri?) {
        val photoImageView = requireView().findViewById<ImageView>(R.id.photo)
        val removeButton = requireView().findViewById<ImageButton>(R.id.remove_photo)

        if (uri == null) return

        try {
            val bitmap = ImageUtils.convertUriToBitmap(requireActivity().contentResolver, uri)
            photoImageView.setImageBitmap(bitmap)
            photoImageView.visibility = View.VISIBLE
            removeButton.visibility = View.VISIBLE

            selectedPhoto = ImageUtils.convertBitmapToBase64(bitmap)
        } catch (_: IOException) { }
    }

    private fun handleRemovePhoto() {
        val photoImageView = requireView().findViewById<ImageView>(R.id.photo)
        photoImageView.visibility = View.GONE
        requireView().findViewById<ImageButton>(R.id.remove_photo).visibility = View.GONE
    }

    private fun handleSubmit() {
        val name = binding.name.editText ?: return
        val searchCategory = binding.searchCategoryInput.editText ?: return

        if (!Validator.validateRequiredField(name, requireContext())) return

        val categories = (binding.categoryRecycler.adapter as AddCategoryAdapter).selected
        if (categories.size == 0) {
            searchCategory.error = getString(R.string.field_is_required)
            return
        }

        viewModel.updateMachine(machine.id, name.text.toString(), selectedPhoto, categories.map { it.id })
    }

    private fun handleSearchCategory(search: String) {
        val filtered = viewModel.categories.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (binding.categoryRecycler.adapter as AddCategoryAdapter).updateDataset(filtered)
    }

    private fun resetView() {
        selectedPhoto = machine.photo

        val bitmap = ImageUtils.convertBase64ToBitmap(machine.photo)
        if (bitmap != null) {
            binding.photo.setImageBitmap(bitmap)
            binding.photo.visibility = View.VISIBLE
            binding.removePhoto.visibility = View.VISIBLE
        }

        binding.name.error = null
        binding.name.editText?.setText(machine.name)
        binding.searchCategoryInput.error = null
        binding.searchCategoryInput.editText?.text = null
        binding.submit.isEnabled = true
        binding.submit.setBackgroundColor(requireContext().getColor(R.color.primaryColor))
        binding.message.visibility = View.INVISIBLE
    }

    private fun loadCategories() {
        viewModel.getCategories()

        viewModel.categories.observe(viewLifecycleOwner) {
            if (it.data != null) {
                val adapter = AddCategoryAdapter(it.data)
                adapter.selected = machine.categories.toMutableList()
                binding.categoryRecycler.adapter = adapter
            }
        }
    }

}
