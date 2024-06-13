package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminHomeBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class AdminHomeFragment : BaseFragment<FragmentAdminHomeBinding>(
    FragmentAdminHomeBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(getString(R.string.home), true)

        binding.generateUser.setOnClickListener { handleGenerateNewUserClick() }
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            binding.name.text = it.name

            if (it.avatar != null) {
                val bitmap = ImageUtils.convertBase64ToBitmap(it.avatar!!)
                if (bitmap != null) binding.avatar.setImageBitmap(bitmap)
            }
        }
    }

    private fun handleGenerateNewUserClick() {
        navController.navigate(R.id.action_admin_home_fragment_to_adminGenerateUserFragment)
    }
}
