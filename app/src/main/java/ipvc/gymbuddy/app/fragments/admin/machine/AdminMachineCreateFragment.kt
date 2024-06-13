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
import androidx.recyclerview.widget.RecyclerView
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.AddCategoryAdapter
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.core.Validator
import ipvc.gymbuddy.app.databinding.FragmentAdminMachineCreateBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.viewmodels.admin.machine.AdminMachineCreateViewModel
import java.io.IOException

class AdminMachineCreateFragment : BaseFragment<FragmentAdminMachineCreateBinding>(
    FragmentAdminMachineCreateBinding::inflate
) {
    private lateinit var viewModel: AdminMachineCreateViewModel
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var gallery: ActivityResultLauncher<PickVisualMediaRequest>

    private var selectedPhoto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        gallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { handleUploadPhoto(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.create_machine))

        resetView()
        loadCategories()

        categoriesRecyclerView = view.findViewById(R.id.category_recycler)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.submit.setOnClickListener { handleSubmit() }
        binding.upload.setOnClickListener { launchPhotoGallery() }
        binding.removePhoto.setOnClickListener { handleRemovePhoto() }
        binding.searchCategoryInput.editText?.addTextChangedListener { handleSearchCategory(it.toString()) }
        viewModel.postData.observe(viewLifecycleOwner) {
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

        val categories = (categoriesRecyclerView.adapter as AddCategoryAdapter).selected
        if (categories.size == 0) {
            searchCategory.error = getString(R.string.field_is_required)
            return
        }

        viewModel.createMachine(name.text.toString(), selectedPhoto, categories.map { it.id })
    }

    private fun handleSearchCategory(search: String) {
        val filtered = viewModel.categories.value?.data?.filter {
            it.name.contains(search, true)
        } ?: listOf()
        (categoriesRecyclerView.adapter as AddCategoryAdapter).updateDataset(filtered)
    }

    private fun resetView() {
        binding.name.error = null
        binding.name.editText?.text = null
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
                categoriesRecyclerView.adapter = AddCategoryAdapter(it.data)
            }
        }
    }

}
