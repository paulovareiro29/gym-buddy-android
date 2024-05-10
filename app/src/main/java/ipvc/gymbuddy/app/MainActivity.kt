package ipvc.gymbuddy.app

import android.os.Bundle
import com.google.android.material.navigation.NavigationBarView
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

        initializeNavigation()
        initializeBottomMenu()
    }

    private fun initializeNavigation() {
        when (viewModel.user.value!!.role.name) {
            "admin" -> navController.setGraph(R.navigation.admin_navigation)
            "trainer" -> navController.setGraph(R.navigation.not_found_navigation)
            "client" -> navController.setGraph(R.navigation.not_found_navigation)
            else -> navController.setGraph(R.navigation.not_found_navigation)
        }
    }

    private fun initializeBottomMenu() {
        val menu = findViewById<NavigationBarView>(R.id.bottom_navigation)
        menu.setOnApplyWindowInsetsListener(null)
        menu.setPadding(0,0,0,0)
        menu.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home_bottom_navigation -> {
                    when (viewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.adminHomeFragment)
                        "trainer" -> navController.setGraph(R.navigation.not_found_navigation)
                        "client" -> navController.setGraph(R.navigation.not_found_navigation)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                    true
                }
                R.id.profile_bottom_navigation -> {
                    navController.navigate(R.id.admin_not_found_fragment)
                    true
                }
                R.id.settings_bottom_navigation -> {
                    navController.navigate(R.id.admin_not_found_fragment)
                    true
                }
                else -> false
            }
        }
    }
}

