package ipvc.gymbuddy.app

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
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
        initializeSidebar()
    }

    private fun initializeNavigation() {
        when (viewModel.user.value!!.role.name) {
            "admin" -> navController.setGraph(R.navigation.admin_navigation)
            "trainer" -> navController.setGraph(R.navigation.trainer_navigation)
            "client" -> navController.setGraph(R.navigation.client_navigation)
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
                        "admin" -> navController.navigate(R.id.admin_home_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_home_fragment)
                        "client" -> navController.navigate(R.id.client_home_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                    true
                }
                R.id.profile_bottom_navigation -> {
                    when (viewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_profile_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_profile_fragment)
                        "client" -> navController.navigate(R.id.client_profile_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                    true
                }
                R.id.settings_bottom_navigation -> {
                    when (viewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_settings_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_settings_fragment)
                        "client" -> navController.navigate(R.id.client_settings_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun initializeSidebar() {
        val drawer = findViewById<DrawerLayout>(R.id.root)
        val sidebar = findViewById<NavigationView>(R.id.sidebar_navigation)

        sidebar.menu.clear()
        when (viewModel.user.value!!.role.name) {
            "admin" -> sidebar.inflateMenu(R.menu.admin_sidebar_menu)
            "trainer" -> sidebar.inflateMenu(R.menu.trainer_sidebar_menu)
            "client" -> sidebar.inflateMenu(R.menu.client_sidebar_menu)
        }

        sidebar.setNavigationItemSelectedListener {
            when (it.itemId) {
                // ADMIN
                R.id.sidebar_item_admin_users_overview -> navController.navigate(R.id.admin_users_overview_fragment)
                R.id.sidebar_item_admin_exercises_overview -> navController.navigate(R.id.admin_exercises_overview_fragment)
                R.id.sidebar_item_admin_machines_overview -> navController.navigate(R.id.admin_machines_overview_fragment)
                R.id.sidebar_item_admin_categories_overview -> navController.navigate(R.id.admin_categories_overview_fragment)

                //TRAINER
                R.id.sidebar_item_trainer_trainingPlans_overview -> navController.navigate(R.id.trainer_trainingplans_overview_fragment)

                // COMMON ROUTES
                R.id.sidebar_item_home -> {
                    when (viewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_home_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_home_fragment)
                        "client" -> navController.navigate(R.id.client_home_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                }
                R.id.sidebar_item_profile -> {
                    when (viewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_profile_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_profile_fragment)
                        "client" -> navController.navigate(R.id.client_profile_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                }
                R.id.sidebar_item_settings -> {
                    when (viewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_settings_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_settings_fragment)
                        "client" -> navController.navigate(R.id.client_settings_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                }
            }

            drawer.close()
            true
        }
    }
}

