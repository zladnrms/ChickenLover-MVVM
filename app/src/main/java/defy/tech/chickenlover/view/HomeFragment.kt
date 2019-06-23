package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics

import defy.tech.chickenlover.R
import defy.tech.chickenlover.adapter.BrandListAdapter
import defy.tech.chickenlover.adapter.RecentReviewListAdapter
import defy.tech.chickenlover.databinding.HomeFragmentBinding
import defy.tech.chickenlover.network.data.RandomChickenResponse
import defy.tech.chickenlover.view.custom.TypeView
import defy.tech.chickenlover.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var brandListAdapter: BrandListAdapter
    private lateinit var reviewListAdapter: RecentReviewListAdapter

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

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        brandListAdapter = BrandListAdapter { item ->
            //openArticleActivity(articleItem)
        }
        brandList.apply {
            adapter = brandListAdapter
            hasFixedSize()
        }

        reviewListAdapter = RecentReviewListAdapter { item ->
            //openArticleActivity(articleItem)
        }
        recentReviewList.apply {
            adapter = reviewListAdapter
            hasFixedSize()
        }
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}
