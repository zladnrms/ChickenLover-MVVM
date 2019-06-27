package defy.tech.chickenlover.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.R
import defy.tech.chickenlover.model.ChickenInfoRepository
import defy.tech.chickenlover.model.UserRepository
import defy.tech.chickenlover.model.VersionInfoRepository
import defy.tech.chickenlover.model.data.ArticleCommentItem
import defy.tech.chickenlover.model.data.ArticleItem
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.user.ChickenInfo
import defy.tech.chickenlover.model.user.User
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.network.data.WriteCommentResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class SearchChickenInfoViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        ChickenInfoRepository(application)
    }

    private val versionRepository by lazy {
        VersionInfoRepository(application)
    }

    private val api by lazy {
        RetrofitInterface.create()
    }

    val check_progress = MutableLiveData<Boolean>()
    val chickenInfoList = MutableLiveData<ArrayList<ChickenInfo>>().apply { value = ArrayList() }

    fun checkVersion() {
        var remote_version = 0

        addDisposable(api.checkChickenInfoVersion("mobile")
            .flatMap { response ->
                remote_version = response.code
                versionRepository.getVersionInfo().toSingle()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { local_data ->
                if (remote_version != local_data.code) {
                    versionRepository.update(remote_version)
                    syncChickenList()
                }
                else
                    check_progress.value = true
            })
    }

    fun syncChickenList() {
        repository.deleteAll()

        addDisposable(api.updateLocalChickenInfo()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { }
            .subscribe { response ->
                if (response.result.equals("success")) {
                    response.result_array?.let {
                        for (item in it) {
                            val item_obj = JSONObject(item)

                            val info_id = item_obj.get("_id").toString().toInt()
                            val brand = item_obj.get("brand") as String
                            val name = item_obj.get("name") as String
                            val type_number = item_obj.get("type_number").toString().toInt()

                            repository.insert(ChickenInfo(null, info_id, brand, name, type_number))
                        }
                    }
                    check_progress.value = true
                }
            })
    }

    fun getChickenList() {
        addDisposable(repository.getAll()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { }
            .subscribe { list->
                chickenInfoList.value?.addAll(list)
                chickenInfoList.value = chickenInfoList.value
            })
    }
}