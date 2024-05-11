package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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

        viewModel.user.observe(viewLifecycleOwner, Observer  { user ->
            user?.let {
                binding.apply {
                    name.text = user.name?.toString() ?: ""
                    firstName.setText(user.name?.toString() ?: "")
                    gender.setText(user.email?.toString() ?: "")
                    address.setText(user.address?.toString() ?: "")
                    role.text = user.role?.name?.toString() ?: ""
                }
            }
        })
    }
}
