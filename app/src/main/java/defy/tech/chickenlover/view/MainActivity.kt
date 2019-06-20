package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.werb.pickphotoview.extensions.string
import com.werb.pickphotoview.util.PickConfig
import defy.tech.chickenlover.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: let {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_layout, HomeFragment(), "home")
                addToBackStack(null)
                commit()
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottom_navigation.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener)
        bottom_navigation.selectedItemId = R.id.action_home
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                switchFragment(HomeFragment.newInstance(), "home", string(R.string.toolbar_title_home))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_board -> {
                switchFragment(BoardFragment.newInstance(), "board", string(R.string.toolbar_title_board))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_chat -> {
                switchFragment(ChatFragment.newInstance(), "chat", string(R.string.toolbar_title_chat))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_sale_info -> {
                switchFragment(InfoFragment.newInstance(), "info", string(R.string.toolbar_title_info))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                switchFragment(MyPageFragment.newInstance(), "mypage", string(R.string.toolbar_title_profile))
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
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_layout, fragment, tag)
            addToBackStack(null)
            commit()
        }
        toolbar_title.text = title
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
                    val data = FileUploadData(index, presenter.getFileName(item, true), item, bitmap)
                    adapter.add(data)
                    adapter.refresh()
                }*/
            }
        }
    }

    companion object {
        fun starterIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
