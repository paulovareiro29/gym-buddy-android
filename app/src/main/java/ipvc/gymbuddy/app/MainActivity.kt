package ipvc.gymbuddy.app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    // private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // setupActionBarWithNavController(navController)

        // authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        /* authenticationViewModel.user.observe(this) {
            findViewById<TextView>(R.id.text).text = it?.email ?: ""
        } */
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /* fun handleButtonClick(v: View?) {
        authenticationViewModel.login("gymbuddy@ipvc.pt", "gymbuddy")
    } */
}

