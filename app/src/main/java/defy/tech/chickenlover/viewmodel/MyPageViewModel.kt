package defy.tech.chickenlover.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import defy.tech.chickenlover.model.UserRepository
import defy.tech.chickenlover.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyPageViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserRepository(application)
    }

    private val _navigateToActivityCall = SingleLiveEvent<Boolean>()
    val navigateToActivityCall: LiveData<Boolean> get() = _navigateToActivityCall

    private val _navigateToSetPhotoCall = SingleLiveEvent<Boolean>()
    val navigateToSetPhotoCall: LiveData<Boolean> get() = _navigateToSetPhotoCall

    fun clickProfile() {
        addDisposable(repository.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError{ Log.d("getUserInfo","onError") }
            .doOnSubscribe { }

            .doOnSuccess { Log.d("getUserInfo","onSuccess") }
            .doOnComplete { Log.d("getUserInfo","onComplete") }
            .subscribe{ user->
                user?.let {
                    if(user.type == 0)
                        _navigateToActivityCall.call()
                    else {

                    }
                }
            })
    }

}
