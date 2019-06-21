package defy.tech.chickenlover.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.R
import defy.tech.chickenlover.model.UserLikeRepository
import defy.tech.chickenlover.model.user.UserLikeBrand
import defy.tech.chickenlover.model.user.UserLikeType
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.network.data.RandomChickenResponse
import defy.tech.chickenlover.util.SingleLiveEvent
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

    private val _selected_brand = MutableLiveData<String>().apply { value = "empty" }
    val selected_brand: LiveData<String> get() = _selected_brand

    private val _selected_type = MutableLiveData<String>().apply { value = "empty" }
    val selected_type: LiveData<String> get() = _selected_type

    val chicken_brands = listOf("선택","bhc","bbq","네네치킨","페리카나","맘스터치","교촌치킨","굽네치킨","처갓집양념치킨","호식이두마리치킨","멕시카나",
        "또래오래","또봉이통닭","지코바치킨","썬더치킨","부어치킨","멕시칸치킨")
    val chicken_types = listOf("선택","후라이드","양념","치즈","간장","파닭","갈릭","매운맛")

    var chickenInfoItem = MutableLiveData<RandomChickenResponse>()

    fun selectBrand(brand: String) {
        _selected_brand.value = brand
    }

    fun selectType(type: String) {
        _selected_type.value = type
    }

    fun getChickenInfo() {
        val way = "choice"
        var brand = selected_brand.value
        var type = selected_type.value

        if(brand.equals("선택"))
            brand = "empty"

        if(type.equals("선택"))
            type = "empty"

        addDisposable(api.getChickenInfo(way, brand, type)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                chickenInfoItem.value = response
            })
    }
}
