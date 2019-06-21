package defy.tech.chickenlover.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.R
import defy.tech.chickenlover.model.UserLikeRepository
import defy.tech.chickenlover.model.user.UserLikeBrand
import defy.tech.chickenlover.model.user.UserLikeType
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.network.data.RandomChickenResponse
import io.defy.chicken.lover.util.RandomPickUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserLikeRepository(application)
    }

    private val api by lazy {
        RetrofitInterface.create()
    }

    var info_name = ObservableField<String>()
    var info_brand = ObservableField<String>()

    var chickenInfoItem = MutableLiveData<RandomChickenResponse>()

    init {
        info_name.set("선택해주세요")
        info_brand.set("선택해주세요")
    }

    fun getChickenInfo() {
        val way = "choice"
        val brand = "empty"
        val type = "empty"

        addDisposable(api.getChickenInfo(way, brand, type)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                chickenInfoItem.value = response
            })
    }
}
