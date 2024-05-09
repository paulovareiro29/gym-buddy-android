package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminHomeBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class AdminHomeFragment : BaseFragment<FragmentAdminHomeBinding>(
    FragmentAdminHomeBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }
}
