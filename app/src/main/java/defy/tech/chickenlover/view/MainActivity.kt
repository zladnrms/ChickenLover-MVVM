package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.werb.pickphotoview.extensions.string
import com.werb.pickphotoview.util.PickConfig
import defy.tech.chickenlover.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment.newInstance()
    private var boardFragment = BoardFragment.newInstance()
    private var chatFragment = ChatFragment.newInstance()
    private var infoFragment = InfoFragment.newInstance()
    private var myPageFragment = MyPageFragment.newInstance()
    private var active : Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: let {
            switchFragment(homeFragment, "home", string(R.string.toolbar_title_home))
        }

        layout_search.setOnClickListener {
            openSearchActivity()
        }

        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottom_navigation.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener)
        bottom_navigation.selectedItemId = R.id.action_home

        supportFragmentManager.beginTransaction().add(R.id.fragment_layout, homeFragment, "home").commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_layout, boardFragment, "board").hide(boardFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_layout, chatFragment, "chat").hide(chatFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_layout, infoFragment, "info").hide(infoFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_layout, myPageFragment, "mypage").hide(myPageFragment).commit()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                switchFragment(homeFragment, "home", string(R.string.toolbar_title_home))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_board -> {
                switchFragment(boardFragment, "board", string(R.string.toolbar_title_board))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_chat -> {
                switchFragment(chatFragment, "chat", string(R.string.toolbar_title_chat))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_sale_info -> {
                switchFragment(infoFragment, "info", string(R.string.toolbar_title_info))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                switchFragment(myPageFragment, "mypage", string(R.string.toolbar_title_profile))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val onNavigationItemReselectedListener = object : BottomNavigationView.OnNavigationItemReselectedListener {
        override fun onNavigationItemReselected(item: MenuItem) {
            //Toast.makeText(this@MainActivity, "Reselected", Toast.LENGTH_SHORT).show()
        }
    }

    fun switchFragment(fragment: Fragment, tag: String, title: String) {
        supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
        toolbar_title.text = title
        if(tag.equals("home"))
            layout_search.visibility = View.VISIBLE
        else
            layout_search.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (bottom_navigation.selectedItemId == R.id.action_home) {
        } else {
            bottom_navigation.selectedItemId = R.id.action_home
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 0) { return }
        data ?: return

        /* WriteFragment로부터 이미지 선택 시 path를 WriteFragment로 전송 */
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            val selectPaths: ArrayList<String> =
                data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT) as ArrayList<String>

            selectPaths.let {
                /*for ((index, item) in it.withIndex()) {
                    val bitmap = presenter.imgPathToBitmap(item)
                    val data = UploadImageItem(index, presenter.getFileName(item, true), item, bitmap)
                    adapter.add(data)
                    adapter.refresh()
                }*/
            }
        }
    }

    private fun openSearchActivity() {
        startActivity(SearchChickenInfoActivity.starterIntent(this))
    }

    companion object {
        fun starterIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
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
