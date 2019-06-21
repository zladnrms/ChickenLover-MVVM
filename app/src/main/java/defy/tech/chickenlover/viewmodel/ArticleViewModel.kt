package defy.tech.chickenlover.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.model.UserRepository
import defy.tech.chickenlover.model.data.ArticleCommentItem
import defy.tech.chickenlover.model.data.ArticleItem
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.user.User
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.network.data.WriteCommentResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class ArticleViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserRepository(application)
    }

    private val api by lazy {
        RetrofitInterface.create()
    }

    private var type = "free"
    var articleListItem = MutableLiveData<ArticleListItem>() // List로부터 받아온
    var articleItem = MutableLiveData<ArticleItem>()
    val articleCommentList = MutableLiveData<ArrayList<ArticleCommentItem>>().apply { value = ArrayList() }

    var comment = ObservableField<String>("")

    fun getArticleInfo() {
        articleListItem.value?.let {
            val articleId = it._id
            val title = it.title

            addDisposable(api.getBoardArticle(type, articleId.toInt(), title)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe { response ->
                    val articleItem = ArticleItem(
                        response._id,
                        response.title,
                        response.writer,
                        response.content,
                        response.img_url,
                        response.create_date,
                        response.thumbs,
                        response.comment_id
                    )
                    this.articleItem.value = articleItem
                })
        }
    }

    fun getArticleComment() {
        articleItem.value?.let {
            val comment_id = it.comment_id

            addDisposable(api.getBoardComment(type, comment_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe { response ->
                    if (response.result.equals("success")) {
                        response.result_array?.let {
                            for (item in it) {
                                val data = ArticleCommentItem(
                                    item._id,
                                    item.name,
                                    item.content,
                                    item.thumbs_up,
                                    item.write_date,
                                    item.invisible
                                )
                                articleCommentList.value?.add(data)
                                articleCommentList.value = articleCommentList.value
                            }
                        }
                    }
                })
        }
    }

    fun writeBoardComment() {
        if(!comment.get()?.trim().equals("")) {
            articleItem.value?.let {
                addDisposable(repository.getUserInfo().toSingle()
                    .flatMap { user ->
                        api.writeBoardComment(type, it._id, user.name, comment.get())
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response ->
                        if (response.result.equals("success")) {
                            getArticleComment()
                        }
                    })
            }
        }
    }

    fun onCommentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        comment.set(s.toString())
    }
}