package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics

import defy.tech.chickenlover.R
import defy.tech.chickenlover.databinding.HomeFragmentBinding
import defy.tech.chickenlover.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var binding : HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics

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

        layout_search.setOnClickListener {
            //val intent = Intent(activity, SearchChickenInfoActivity::class.java)
            //startActivity(intent)
        }

        card_chicken_info.setOnClickListener {
            /*
            val intent = Intent(activity, ChickenInfoActivity::class.java)
            presenter.getChickenInfo()?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(activity, iv_chicken_img, "chickenImg")
                    intent.putExtra("infoId", it._id)
                    intent.putExtra("typeNumber", it.type_number)
                    startActivity(intent, options.toBundle())
                } else {
                    intent.putExtra("infoId", it._id)
                    intent.putExtra("typeNumber", it.type_number)
                    startActivity(intent)
                }
            }

            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "card_click")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)*/
        }

        btn_id.setOnClickListener {
            /*
            val brand = RandomPickUtil.randomBrandPick()
            val type = RandomPickUtil.randomTypePick()

            presenter.getChickenInfo("choice", brand, type)*/
        }

    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}
