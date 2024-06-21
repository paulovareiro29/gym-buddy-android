package ipvc.gymbuddy.app

import android.os.Bundle
import ipvc.gymbuddy.app.core.BaseActivity
import ipvc.gymbuddy.app.core.Navigator
import ipvc.gymbuddy.app.viewmodels.AuthenticationViewModel

class AuthenticationActivity : BaseActivity(R.layout.activity_authentication, R.id.auth_nav_host_fragment) {
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()

        viewModel.user.observe(this) {
            if (it != null) {
                Navigator.resetNavigationTo(MainActivity::class.java, this)
            }
        }
    }
}