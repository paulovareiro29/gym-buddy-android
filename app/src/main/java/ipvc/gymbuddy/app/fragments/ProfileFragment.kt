package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentProfileBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.profile))
        setRootFragment()

        viewModel.user.observe(viewLifecycleOwner, Observer  { user ->
            if (user == null) return@Observer
            binding.apply {
                name.text = user.name
                email.text = user.email
                address.text = user.address
                role.text  = user.role.name.takeIf { it.isNotEmpty() }?.replaceFirstChar { it.uppercaseChar() }
            }
        })
    }
}
