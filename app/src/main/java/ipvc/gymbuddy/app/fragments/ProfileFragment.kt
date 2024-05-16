package ipvc.gymbuddy.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentProfileBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import java.util.Locale

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
            if (user == null) return@Observer
            binding.apply {
                name.text = user.name
                email.text = user.email
                address.text = user.address
                //birthdate.text
                val roleName = user.role?.name?.let { name ->
                    name.takeIf { it.isNotEmpty() }?.let {
                        it.substring(0, 1).uppercase(Locale.getDefault()) + it.substring(1)
                    }
                }
                role.text = roleName
            }
        })
    }
}
