package ipvc.gymbuddy.app

import android.os.Bundle
import ipvc.gymbuddy.app.core.BaseActivity
import ipvc.gymbuddy.app.core.Navigator
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class MainActivity : BaseActivity(R.layout.activity_main, R.id.nav_host_fragment) {
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()

        if (viewModel.user.value == null) {
            Navigator.resetNavigationTo(AuthenticationActivity::class.java, this)
            return
        }

        when (viewModel.user.value!!.role.name) {
            "admin" -> navController.setGraph(R.navigation.admin_navigation)
            "trainer" -> navController.setGraph(R.navigation.not_found_navigation)
            "client" -> navController.setGraph(R.navigation.not_found_navigation)
            else -> navController.setGraph(R.navigation.not_found_navigation)
        }
    }
}

