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

class BrandInfoViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserRepository(application)
    }

    private val api by lazy {
        RetrofitInterface.create()
    }

}