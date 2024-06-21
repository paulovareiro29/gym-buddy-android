package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentWelcomeOfflineBinding

class WelcomeOfflineFragment : BaseFragment<FragmentWelcomeOfflineBinding>(
    FragmentWelcomeOfflineBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {
            navController.navigateUp()
        }
    }
}
