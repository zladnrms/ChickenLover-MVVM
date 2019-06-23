package defy.tech.chickenlover.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.R
import defy.tech.chickenlover.model.UserLikeRepository
import defy.tech.chickenlover.model.data.BrandSummaryItem
import defy.tech.chickenlover.model.data.ChickenReviewItem
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

    val brandList = MutableLiveData<ArrayList<BrandSummaryItem>>().apply { value = ArrayList() }
    val reviewList = MutableLiveData<ArrayList<ChickenReviewItem>>().apply { value = ArrayList() }

    val chicken_brands = listOf("선택","bhc","bbq","네네치킨","페리카나","맘스터치","교촌치킨","굽네치킨","처갓집양념치킨","호식이두마리치킨","멕시카나",
        "또래오래","또봉이통닭","지코바치킨","썬더치킨","부어치킨","멕시칸치킨")
    val chicken_types = listOf("선택","후라이드","양념","치즈","간장","파닭","갈릭","매운맛")
}
