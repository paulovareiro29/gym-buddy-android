package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminHomeBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel
import java.util.Locale

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
        binding.name.text = viewModel.user.value!!.name
        binding.role.text = viewModel.user.value!!.role.name.replaceFirstChar { it.titlecase(Locale.ROOT) }
    }
}
