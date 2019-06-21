package defy.tech.chickenlover.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class BoardViewModel : DisposableViewModel() {
    private val api by lazy {
        RetrofitInterface.create()
    }

    private val limit = 15
    private var index = 0

    val board_types = listOf("자유게시판","실시간정보")

    private val _selected_type = MutableLiveData<String>().apply { value = "free" }
    val selected_type: LiveData<String> get() = _selected_type

    private val _navigateToActivityCall = SingleLiveEvent<Boolean>()
    val navigateToActivityCall: LiveData<Boolean> get() = _navigateToActivityCall

    val articleList = MutableLiveData<ArrayList<ArticleListItem>>().apply { value = ArrayList() }

    fun selectType(type: String) {
        _selected_type.value = type
    }

    fun openWriteActivity() {
        Log.d("openWriteActivity", "called")
        _navigateToActivityCall.call()
    }

    fun getArticleList() {

        var type = selected_type.value

        if(type.equals("자유게시판"))
            type = "free"

        if(type.equals("실시간정보"))
            type = "info"

        addDisposable(api.getBoardArticleList(type, index, limit)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  }
            .subscribe{ response->
                if(response.result.equals("success"))
                {
                    response.result_array?.let {
                        for(item in it)
                        {
                            val item_obj = JSONObject(item)
                            var img_exist = false
                            if(!item_obj.get("img_url").toString().equals("null"))
                                img_exist = true

                            val data = ArticleListItem(item_obj.get("_id") as String, item_obj.get("name") as String, item_obj.get("title") as String, img_exist, item_obj.get("create_date") as String, item_obj.get("comment_amount") as String)
                            articleList.value?.add(data)
                            articleList.value = articleList.value
                        }
                    }
                }
            })
    }

    fun setRecyclerViewScrollListener(list: RecyclerView) {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager =  list.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisible >= (totalItemCount.minus(1)) && lastVisible >= index + 14) {
                    Log.d("onScrolled", "index : $index, lastVisible : $lastVisible, totalItemCount : $totalItemCount");
                    index += 15
                    getArticleList()
                }
            }
        }
        list.addOnScrollListener(scrollListener)
    }
}
