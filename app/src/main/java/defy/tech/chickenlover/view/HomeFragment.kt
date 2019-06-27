package defy.tech.chickenlover.view

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics

import defy.tech.chickenlover.R
import defy.tech.chickenlover.adapter.BrandListAdapter
import defy.tech.chickenlover.adapter.RecentReviewListAdapter
import defy.tech.chickenlover.adapter.deco.GridSpacingItemDecoration
import defy.tech.chickenlover.databinding.HomeFragmentBinding
import defy.tech.chickenlover.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var brandListAdapter: BrandListAdapter
    private lateinit var reviewListAdapter: RecentReviewListAdapter

    private val spanCount = 3
    private val spacing = 5
    private val includeEdge = true

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        context?.let {
            firebaseAnalytics = FirebaseAnalytics.getInstance(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        val mGridLayoutManager = GridLayoutManager(activity, 3)
        brandListAdapter = BrandListAdapter { item ->
            //openArticleActivity(articleItem)
        }
        brandList.apply {
            this.layoutManager = mGridLayoutManager
            hasFixedSize()
            this.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
            adapter = brandListAdapter
        }

        reviewListAdapter = RecentReviewListAdapter { item ->
            //openArticleActivity(articleItem)
        }
        recentReviewList.apply {
            hasFixedSize()
            adapter = reviewListAdapter
        }

        homeViewModel.brandList.observe(this, Observer {
            brandListAdapter.setList(it)
        })

        layout_search.setOnClickListener {
            openSearchActivity()
        }

        displayBrandList()
    }

    private fun displayBrandList() {
        homeViewModel.getBrandList()
    }

    override fun onPause() {
        super.onPause()
        activity?.overridePendingTransition(0, 0)
    }

    override fun onResume() {
        activity?.overridePendingTransition(0,0)
        super.onResume()
    }

    private fun openSearchActivity() {
        startActivity(SearchChickenInfoActivity.starterIntent(activity as MainActivity))
    }
}
