package ipvc.gymbuddy.app.fragments.admin

import android.os.Bundle
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminGenerateUserBinding
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class AdminGenerateUserFragment : BaseFragment<FragmentAdminGenerateUserBinding>(
    FragmentAdminGenerateUserBinding::inflate
) {
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

}