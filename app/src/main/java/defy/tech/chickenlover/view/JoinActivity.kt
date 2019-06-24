package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import defy.tech.chickenlover.R

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    companion object {
        fun starterIntent(context: Context): Intent {
            return Intent(context, JoinActivity::class.java)
        }
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
