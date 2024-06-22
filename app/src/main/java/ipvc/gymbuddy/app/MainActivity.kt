package ipvc.gymbuddy.app

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import ipvc.gymbuddy.app.core.BaseActivity
import ipvc.gymbuddy.app.core.Navigator
import ipvc.gymbuddy.app.core.NetworkReceiver
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class MainActivity : BaseActivity(R.layout.activity_main, R.id.nav_host_fragment) {
    private lateinit var authViewModel: AuthenticationViewModel

    private var networkListener: NetworkReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = getViewModel()
        authViewModel.init()

        authViewModel.initialized.observe(this) {
            if (it == true) {
                findViewById<NavigationBarView>(R.id.bottom_navigation).visibility = View.VISIBLE
            } else {
                findViewById<NavigationBarView>(R.id.bottom_navigation).visibility = View.GONE
            }
        }

        authViewModel.user.observe(this) {
            if (it == null) {
                Navigator.resetNavigationTo(AuthenticationActivity::class.java, this)
            } else {
                initializeNavigation()
                initializeBottomMenu()
                initializeSidebar()
                initializeNetworkChanges()
            }
        }
    }

    override fun onPause() {
        if (networkListener != null) {
            unregisterReceiver(networkListener)
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        initializeNetworkChanges()
    }

    private fun initializeNavigation() {
        when (authViewModel.user.value!!.role.name) {
            "admin" -> navController.setGraph(R.navigation.admin_navigation)
            "trainer" -> navController.setGraph(R.navigation.trainer_navigation)
            "default" -> navController.setGraph(R.navigation.client_navigation)
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
                    when (authViewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_home_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_home_fragment)
                        "default" -> navController.navigate(R.id.client_home_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                    true
                }
                R.id.profile_bottom_navigation -> {
                    when (authViewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_profile_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_profile_fragment)
                        "default" -> navController.navigate(R.id.client_profile_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                    true
                }
                R.id.settings_bottom_navigation -> {
                    when (authViewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_settings_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_settings_fragment)
                        "default" -> navController.navigate(R.id.client_settings_fragment)
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
        val bottomMenu = findViewById<NavigationBarView>(R.id.bottom_navigation)
        val sidebar = findViewById<NavigationView>(R.id.sidebar_navigation)

        sidebar.menu.clear()
        when (authViewModel.user.value!!.role.name) {
            "admin" -> sidebar.inflateMenu(R.menu.admin_sidebar_menu)
            "trainer" -> sidebar.inflateMenu(R.menu.trainer_sidebar_menu)
            "default" -> sidebar.inflateMenu(R.menu.client_sidebar_menu)
        }

        sidebar.setNavigationItemSelectedListener {
            when (it.itemId) {
                // ADMIN
                R.id.sidebar_item_admin_users_overview -> navController.navigate(R.id.admin_users_overview_fragment)
                R.id.sidebar_item_admin_exercises_overview -> navController.navigate(R.id.admin_exercises_overview_fragment)
                R.id.sidebar_item_admin_machines_overview -> navController.navigate(R.id.admin_machines_overview_fragment)
                R.id.sidebar_item_admin_categories_overview -> navController.navigate(R.id.admin_categories_overview_fragment)
                R.id.sidebar_item_admin_metric_types_overview -> navController.navigate(R.id.admin_metric_types_overview_fragment)

                //TRAINER
                R.id.sidebar_item_trainer_trainingplans_overview -> navController.navigate(R.id.trainer_trainingplans_overview_fragment)
                R.id.sidebar_item_trainer_listclients_overview -> navController.navigate(R.id.trainer_listclients_overview_fragment)

                //CLIENT
                R.id.sidebar_client_metrics_overview_item -> navController.navigate(R.id.client_metrics_overview_fragment)
                R.id.sidebar_client_training_plan_overview_item -> {
                    val bundle = Bundle().apply {
                        putString("userId", authViewModel.user.value?.id)
                    }
                    navController.navigate(R.id.client_user_plan_overview_fragment, bundle)
                }

                // COMMON ROUTES
                R.id.sidebar_item_home -> {
                    bottomMenu.selectedItemId = R.id.home_bottom_navigation
                    when (authViewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_home_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_home_fragment)
                        "default" -> navController.navigate(R.id.client_home_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                }
                R.id.sidebar_item_profile -> {
                    bottomMenu.selectedItemId = R.id.profile_bottom_navigation
                    when (authViewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_profile_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_profile_fragment)
                        "default" -> navController.navigate(R.id.client_profile_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                }
                R.id.sidebar_item_settings -> {
                    bottomMenu.selectedItemId = R.id.settings_bottom_navigation
                    when (authViewModel.user.value!!.role.name) {
                        "admin" -> navController.navigate(R.id.admin_settings_fragment)
                        "trainer" -> navController.navigate(R.id.trainer_settings_fragment)
                        "default" -> navController.navigate(R.id.client_settings_fragment)
                        else -> navController.setGraph(R.navigation.not_found_navigation)
                    }
                }
            }

            drawer.close()
            true
        }
    }

    private fun initializeNetworkChanges() {
        networkListener = NetworkReceiver { isOnline ->
            val statusBanner = findViewById<LinearLayout>(R.id.network_status) ?: return@NetworkReceiver
            statusBanner.visibility = if (isOnline) View.GONE else View.VISIBLE
        }

        registerReceiver(networkListener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
}

