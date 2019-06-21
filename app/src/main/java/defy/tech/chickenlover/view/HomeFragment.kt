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

        viewModel.chickenInfoItem.observe(this, Observer { item ->
            startFadeInAni()
            setChickenInfo(item.name, item.brand)
            setChickenImage(item.type_number)
            setChickenType(item.type_array)
        })

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

        setSpinner()
    }

    private fun startFadeInAni() {
        val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        layout_in_card.animation = fadeIn
    }

    private fun setChickenInfo(item_name: String, item_brand: String) {
        layout_chicken_type.removeAllViewsInLayout()
        tv_chicken_info_name.text = item_name
        tv_chicken_info_brand.text = item_brand
    }

    private fun setChickenImage(type_number: Int) {
        when (type_number) {
            0 -> iv_chicken_img.setImageResource(R.drawable.fried)
            1 -> iv_chicken_img.setImageResource(R.drawable.seasoned_fried)
            2 -> iv_chicken_img.setImageResource(R.drawable.cheese_fried)
            3 -> iv_chicken_img.setImageResource(R.drawable.soy_fried)
            4 -> iv_chicken_img.setImageResource(R.drawable.green_onion_fried)
            5 -> iv_chicken_img.setImageResource(R.drawable.garlic_fried)
            6 -> iv_chicken_img.setImageResource(R.drawable.peoper_fried)
            else -> R.drawable.fried
        }
    }

    private fun setChickenType(str_type_array: String) {
        val obj = JSONObject(str_type_array)
        val type_array = ArrayList<String>()
        for (key: String in obj.keys()) {
            type_array.add(obj.get(key).toString())
        }

        for (item in type_array) {
            val typeView = TypeView(context as MainActivity, item)
            layout_chicken_type.addView(typeView)
        }
    }

    private fun setSpinner() {
        spinner_brand.setItems(viewModel.chicken_brands)
        spinner_brand.setOnItemSelectedListener { view, position, id, item ->
            viewModel.selectBrand(item.toString())
        }
        spinner_type.setItems(viewModel.chicken_types)
        spinner_type.setOnItemSelectedListener { view, position, id, item ->
            viewModel.selectType(item.toString())
        }
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}
