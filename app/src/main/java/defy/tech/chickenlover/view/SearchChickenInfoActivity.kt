package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import defy.tech.chickenlover.R
import defy.tech.chickenlover.adapter.SearchChickenInfoListAdapter
import defy.tech.chickenlover.databinding.ActivitySearchChickenInfoBinding
import defy.tech.chickenlover.viewmodel.SearchChickenInfoViewModel
import kotlinx.android.synthetic.main.activity_search_chicken_info.*

class SearchChickenInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchChickenInfoBinding
    private lateinit var searchChickenInfoViewModel: SearchChickenInfoViewModel
    private lateinit var searchChickenInfoListAdapter: SearchChickenInfoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchChickenInfoViewModel = ViewModelProviders.of(this).get(SearchChickenInfoViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_chicken_info)
        binding.viewModel = searchChickenInfoViewModel
        binding.lifecycleOwner = this

        searchChickenInfoListAdapter = SearchChickenInfoListAdapter { item ->

        }
        searchList.apply {
            adapter = searchChickenInfoListAdapter
            hasFixedSize()
        }

        searchChickenInfoViewModel.chickenInfoList.observe(this, Observer {
            searchChickenInfoListAdapter.setList(it)
        })

        searchChickenInfoViewModel.check_progress.observe(this, Observer {
            displayChickenList()
        })

        searchChickenInfoViewModel.checkVersion()
    }

    private fun displayChickenList() {
        searchChickenInfoViewModel.getChickenList()
    }

    companion object {
        fun starterIntent(context: Context): Intent {
            return Intent(context, SearchChickenInfoActivity::class.java)
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
