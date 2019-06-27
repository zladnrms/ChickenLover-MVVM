package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import defy.tech.chickenlover.R
import defy.tech.chickenlover.databinding.ActivityChickenInfoBinding
import defy.tech.chickenlover.viewmodel.ChickenInfoViewModel
import kotlinx.android.synthetic.main.activity_chicken_info.*

class ChickenInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChickenInfoBinding
    private lateinit var articleViewModel: ChickenInfoViewModel
    //private lateinit var chickenInfoCommentListAdapter: ChickenInfoCommentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleViewModel = ViewModelProviders.of(this).get(ChickenInfoViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chicken_info)
        binding.viewModel = articleViewModel
        binding.lifecycleOwner = this

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

    }

    companion object {
        fun starterIntent(context: Context): Intent {
            return Intent(context, ChickenInfoActivity::class.java)
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

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
