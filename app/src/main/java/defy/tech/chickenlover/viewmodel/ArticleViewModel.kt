package defy.tech.chickenlover.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.model.UserRepository
import defy.tech.chickenlover.model.data.ArticleCommentItem
import defy.tech.chickenlover.model.data.ArticleItem
import defy.tech.chickenlover.model.data.ArticleLikeItem
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.user.User
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.network.data.WriteCommentResponse
import defy.tech.chickenlover.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class ArticleViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserRepository(application)
    }

    private val api by lazy {
        RetrofitInterface.create()
    }

    private var type = "free"

    private val _navigateToToastCall = SingleLiveEvent<String>()
    val navigateToToastCall: LiveData<String> get() = _navigateToToastCall

    var articleListItem = MutableLiveData<ArticleListItem>() // List로부터 받아온
    var articleItem = MutableLiveData<ArticleItem>()
    val articleCommentList = MutableLiveData<ArrayList<ArticleCommentItem>>().apply { value = ArrayList() }

    val articleLikeList = MutableLiveData<ArrayList<ArticleLikeItem>>().apply { value = ArrayList() }

    var comment = ObservableField<String>("")

    fun getArticleInfo() {
        articleListItem.value?.let {
            val articleId = it._id
            var send_type = 0

            if (type.equals("free"))
                send_type = 0

            if (type.equals("info"))
                send_type = 1

            addDisposable(api.getBoardArticle(send_type, articleId, null, null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe { response ->
                    val articleItem = ArticleItem(
                        response._id,
                        response.type,
                        response.hashed_key,
                        response.writer,
                        response.title,
                        response.content,
                        response.img_url,
                        response.write_date,
                        response.comment_amount,
                        response.like_amount
                    )
                    this.articleItem.value = articleItem
                })
        }
    }

    fun getArticleComment() {
        articleItem.value?.let {
            val article_id = it._id

            addDisposable(api.getBoardComment(article_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doOnError {
                    it.printStackTrace() }
                .subscribe { response ->
                    if (response.result.equals("success")) {
                        response.result_array?.let {
                            for (item in it) {
                                val item_obj = JSONObject(item)

                                val data = ArticleCommentItem(
                                    item_obj.getInt("_id"),
                                    item_obj.getString("writer"),
                                    item_obj.getString("content"),
                                    item_obj.getString("write_date"),
                                    item_obj.getInt("like_amount")
                                )
                                articleCommentList.value?.add(data)
                                articleCommentList.value = articleCommentList.value
                            }
                        }
                    }
                })
        }
    }

    fun writeArticleComment() {
        if (!comment.get()?.trim().equals("")) {
            articleItem.value?.let {articleItem ->
                addDisposable(repository.getUserInfo().toSingle()
                    .flatMap { user ->
                        api.writeBoardComment(articleItem._id, user.hashed_key, comment.get())
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response ->
                        if (response.result.equals("success")) {
                            val data = ArticleCommentItem(
                                response.last_id,
                                response.writer,
                                comment.get(),
                                "방금 전",
                                0
                            )
                            articleCommentList.value?.add(data)
                            articleCommentList.value = articleCommentList.value
                        }
                    })
            }
        }
    }

    fun getArticleLike() {
        articleItem.value?.let {
            addDisposable(api.getBoardLike(it._id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.result.equals("success")) {
                        response.result_array?.let {
                            for (item in it) {
                                val item_obj = JSONObject(item)

                                val data = ArticleLikeItem(
                                    item_obj.getInt("_id"),
                                    item_obj.getString("hashed_key")
                                )
                                articleLikeList.value?.add(data)
                                articleLikeList.value = articleLikeList.value
                            }
                        }
                    }
                })
        }
    }

    fun triggerArticleLike() {
        articleItem.value?.let {articleItem ->
            addDisposable(repository.getUserInfo().toSingle()
                .flatMap { user ->
                    api.triggerBoardLike(articleItem._id, user.hashed_key)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.result.equals("success")) {
                        val data = ArticleLikeItem(
                            response.last_id,
                            response.hashed_key
                        )
                        articleLikeList.value?.add(data)
                        articleLikeList.value = articleLikeList.value
                    } else if(response.result.equals("already")) {
                        _navigateToToastCall.call()
                    }
                })
        }
    }

    fun onCommentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        comment.set(s.toString())
    }
}