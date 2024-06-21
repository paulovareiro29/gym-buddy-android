package ipvc.gymbuddy.app.fragments.admin.user

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ipvc.gymbuddy.api.models.User
import ipvc.gymbuddy.app.R
import ipvc.gymbuddy.app.adapters.ContractAdapter
import ipvc.gymbuddy.app.core.BaseFragment
import ipvc.gymbuddy.app.databinding.FragmentAdminUserIndividualBinding
import ipvc.gymbuddy.app.utils.ImageUtils
import ipvc.gymbuddy.app.utils.StringUtils
import ipvc.gymbuddy.app.viewmodels.admin.user.AdminUserIndividualViewModel

class AdminUserIndividualFragment : BaseFragment<FragmentAdminUserIndividualBinding>(
    FragmentAdminUserIndividualBinding::inflate
) {
    private lateinit var viewModel: AdminUserIndividualViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel()

        try {
            user = Gson().fromJson(arguments?.getString("data"), User::class.java)
        } catch (_: JsonSyntaxException) {
            navController.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbar(user.name ?: getString(R.string.view_user))


        binding.apply {
            if (user.avatar != null) {
                val bitmap = ImageUtils.convertBase64ToBitmap(user.avatar!!)
                if (bitmap != null) avatarImageButton.setImageBitmap(bitmap)
            }

            contractsRecyclerView.layoutManager = LinearLayoutManager(context)
            name.text = user.name
            email.text = user.email
            address.text = user.address
            role.text  = StringUtils.capitalize(user.role.name)

            addContractButton.setOnClickListener { handleCreateContact() }
        }

        if (user.role.name == "default") {
            binding.userContracts.visibility = View.VISIBLE
            loadContracts()
        }
    }

    private fun handleCreateContact() {
        val bundle = Bundle().apply {
            putString("userId", user.id)
        }
        navController.navigate(R.id.action_admin_user_individual_fragment_to_admin_add_contract_fragment, bundle)
    }

    private fun loadContracts() {
        viewModel.getContracts(user.id)
        viewModel.contracts.observe(viewLifecycleOwner) {
            if (!it.data.isNullOrEmpty()) {
                binding.contractsRecyclerView.adapter = ContractAdapter(it.data)
            }
        }
    }
}
