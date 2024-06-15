package ipvc.gymbuddy.app.fragments

import android.os.Bundle
import android.view.View
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentOfflineBinding

class OfflineFragment : BaseFragment<FragmentOfflineBinding>(
    FragmentOfflineBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadToolbar(getString(R.string.oops))

        binding.back.setOnClickListener {
            navController.navigateUp()
        }
    }
}
