package defy.tech.chickenlover.view

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import defy.tech.chickenlover.R
import defy.tech.chickenlover.adapter.ArticleCommentListAdapter
import defy.tech.chickenlover.databinding.ActivityArticleBinding
import defy.tech.chickenlover.model.data.ArticleItem
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.view.custom.ArticleImageViewLayout
import defy.tech.chickenlover.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_article.*
import org.json.JSONArray
import org.json.JSONObject

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArticleBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleCommentListAdapter: ArticleCommentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_article)
        binding.viewModel = articleViewModel
        binding.lifecycleOwner = this

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        articleCommentListAdapter = ArticleCommentListAdapter { item ->

        }
        articleCommentList.apply {
            adapter = articleCommentListAdapter
            hasFixedSize()
        }

        articleViewModel.apply {
            articleListItem.observe(this@ArticleActivity, Observer {
                articleViewModel.getArticleInfo()
            })
            articleItem.observe(this@ArticleActivity, Observer {
                setContents(it)
                articleViewModel.getArticleComment()
                articleViewModel.getArticleLike()
            })
            articleCommentList.observe(this@ArticleActivity, Observer {
                articleCommentListAdapter.setList(it)
            })
            articleLikeList.observe(this@ArticleActivity, Observer {
                btn_like_up.text = it.size.toString()
            })
            navigateToToastCall.observe(this@ArticleActivity, Observer {
                toastmessage()
            })
        }

        btn_like_up.setOnClickListener {
            articleViewModel.triggerArticleLike()
        }

        setDataFromIntent()
    }

    private fun toastmessage() {
        Toast.makeText(this@ArticleActivity, "이미 추천하신 글입니다!", Toast.LENGTH_LONG).show()
    }

    private fun setContents(articleItem: ArticleItem) {
        articleItem.let {
            it.title?.let {
                tv_title.text = it
            }
            it.writer?.let {
                tv_writer.text = it
                tv_profile.text = it[0].toString()
            }
            it.content?.let {
                setContents(it)
            }
            it.img_url?.let{
                setImages(it)
            }
        }
    }

    private fun setContents(content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_content.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY))
        } else {
            tv_content.setText(Html.fromHtml(content))
        }
    }

    private fun setImages(list: ArrayList<String>) {
        for (item in list) {
            val layout = ArticleImageViewLayout(applicationContext)
            layout.setImageView(item)
            layout_img.addView(layout)
        }
    }

    private fun setDataFromIntent() {
        val intent = intent
        articleViewModel.articleListItem.value = intent.getParcelableExtra(EXTRA_ARTICLE)
    }

    companion object {
        private const val EXTRA_ARTICLE = "ARTICLE_ITEM"

        fun starterIntent(context: Context, articleListItem: ArticleListItem): Intent {
            return Intent(context, ArticleActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE, articleListItem)
            }
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
