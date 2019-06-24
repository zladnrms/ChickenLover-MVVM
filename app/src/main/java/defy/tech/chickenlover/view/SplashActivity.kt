package defy.tech.chickenlover.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import defy.tech.chickenlover.R
import defy.tech.chickenlover.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        splashViewModel.navigateToActivityCall.observe(this, Observer {
            openMainActivity()
            finish()
        })

        splashViewModel.initLocalVersion()
        splashViewModel.login()
    }

    private fun openMainActivity() {
        startActivity(MainActivity.starterIntent(this))
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onResume() {
        overridePendingTransition(0,0)
        super.onResume()
    }
}
