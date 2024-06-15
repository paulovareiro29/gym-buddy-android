package ipvc.gymbuddy.app.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.AsyncData
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentProfileBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.utils.NetworkUtils
import ipvc.gymbuddy.app.utils.StringUtils
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import ipvc.gymbuddy.app.viewmodels.ProfileViewModel
import java.io.IOException

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var viewModel: ProfileViewModel
    private lateinit var gallery: ActivityResultLauncher<PickVisualMediaRequest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationViewModel = getViewModel()
        viewModel = getViewModel()

        gallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { handleUploadPhoto(it) }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.profile), true)

        authenticationViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) return@observe
            binding.apply {
                if (user.avatar != null) {
                    val bitmap = ImageUtils.convertBase64ToBitmap(user.avatar!!)
                    if (bitmap != null) avatarImageButton.setImageBitmap(bitmap)
                }

                name.text = user.name
                email.text = user.email
                address.text = user.address
                role.text  = StringUtils.capitalize(user.role.name)
            }
        }

        binding.avatarImageButton.setOnClickListener { launchPhotoGallery() }
        binding.uploadAvatarButton.setOnClickListener { launchPhotoGallery() }
        binding.editButton.setOnClickListener {
            if (NetworkUtils.isOffline(requireContext())) {
                navController.navigate(
                    when (authenticationViewModel.user.value!!.role.name) {
                    "admin" -> R.id.admin_offline_fragment
                    "trainer" -> R.id.trainer_offline_fragment
                    "default" -> R.id.client_offline_fragment
                    else -> {R.id.not_found_navigation}
                })
                return@setOnClickListener
            }
            when (authenticationViewModel.user.value!!.role.name) {
                "admin" -> navController.navigate(R.id.admin_edit_profile_fragment)
                "trainer" -> navController.navigate(R.id.trainer_edit_profile_fragment)
                "default" -> navController.navigate(R.id.client_edit_profile_fragment)
            }
        }

        viewModel.updateData.observe(viewLifecycleOwner) {
            when (it.status) {
                AsyncData.Status.SUCCESS -> {
                    viewModel.refreshAuthenticated()
                }
                else -> {}
            }
        }
    }

    private fun launchPhotoGallery() {
        if (NetworkUtils.isOffline(requireContext())) return
        gallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun handleUploadPhoto(uri: Uri?) {
        if (uri == null) return

        try {
            val bitmap = ImageUtils.convertUriToBitmap(requireActivity().contentResolver, uri)
            val base64 = ImageUtils.convertBitmapToBase64(bitmap)

            viewModel.updateAvatar(authenticationViewModel.user.value!!.id, base64)
        } catch (_: IOException) { }
    }
}
