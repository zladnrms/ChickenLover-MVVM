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
import com.werb.pickphotoview.PickPhotoView

import defy.tech.chickenlover.R
import defy.tech.chickenlover.RxBus.RxBus
import defy.tech.chickenlover.databinding.MyPageFragmentBinding
import defy.tech.chickenlover.viewmodel.MyPageViewModel
import kotlinx.android.synthetic.main.my_page_fragment.*

class MyPageFragment : Fragment() {

    private lateinit var binding: MyPageFragmentBinding
    private lateinit var myPageViewModel: MyPageViewModel

    companion object {
        fun newInstance() = MyPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_page_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myPageViewModel = ViewModelProviders.of(this).get(MyPageViewModel::class.java)
        binding.viewModel = myPageViewModel
        binding.lifecycleOwner = this

        myPageViewModel.navigateToActivityCall.observe(this, Observer {
            openJoinActivity()
        })

        layout_profile.setOnClickListener {
            myPageViewModel.clickProfile()
        }

        profile_go_select_fb.setOnClickListener {
            //val intent = Intent(activity, SelectFavoriteBrandActivity::class.java)
            //startActivity(intent)
        }

        profile_go_select_ft.setOnClickListener {
            //val intent = Intent(activity, SelectFavoriteTypeActivity::class.java)
            //startActivity(intent)
        }

        /* Register Event */
        /*
        RxBus.listen(ImagePickResultEvent::class.java).subscribe{
            /* 이미지 경로값을 받아온 후 리스트에 배열*/
            if(it.from.equals("profile"))
            {
                val file = File(it.imagesPath.get(0))
                val imageUri = Uri.fromFile(file)

                Glide.with(activity as MainActivity)
                    .load(imageUri)
                    .into(profile_image)

                /* 확정 이후 서버에 업로드 */
            }
        }*/
    }

    private fun openJoinActivity() {
        startActivity(JoinActivity.starterIntent(activity as Context))
    }

    private fun openPickPhoto() {
        PickPhotoView.Builder(activity as MainActivity)
            .setPickPhotoSize(1)                  // select image size
            .setClickSelectable(true)             // click one image immediately close and return image
            .setShowCamera(true)                  // is show camera
            .setSpanCount(4)                      // span count
            .setLightStatusBar(true)              // lightStatusBar used in Android M or higher
            .setStatusBarColor(R.color.pick_white)     // statusBar color
            .setToolbarColor(R.color.pick_white)       // toolbar color
            .setToolbarTextColor(R.color.pick_black)   // toolbar text color
            .setSelectIconColor(R.color.pick_black)     // select icon color
            .setShowGif(true)                    // is show gif
            .start()
    }

    override fun onPause() {
        super.onPause()
        activity?.overridePendingTransition(0, 0)
    }

    override fun onResume() {
        activity?.overridePendingTransition(0,0)
        super.onResume()
    }
}
