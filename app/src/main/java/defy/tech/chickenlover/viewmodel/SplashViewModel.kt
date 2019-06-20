package defy.tech.chickenlover.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import defy.tech.chickenlover.model.UserRepository
import defy.tech.chickenlover.model.user.User
import defy.tech.chickenlover.network.RetrofitInterface
import defy.tech.chickenlover.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashViewModel(application: Application) : DisposableAndroidViewModel(application) {
    private val repository by lazy {
        UserRepository(application)
    }
    private val api by lazy {
        RetrofitInterface.create()
    }

    private val _navigateToActivityCall = SingleLiveEvent<Boolean>()
    val navigateToActivityCall: LiveData<Boolean> get() = _navigateToActivityCall

    fun login() {
        addDisposable(repository.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ user->
                user?.let {
                    Log.d("SplashViewModel 45★", "guest ID : ${user.guest_id} , hashedvalue : ${user.hashed_value}")
                    user.guest_id?.let { guestId->
                        loginAsGuest("mobile", user.hashed_value)
                    } ?: user.name?.let {
                        loginAsAuto("mobile", 1, user.hashed_value)
                    }
                } ?: joinAsGuest()
            })
    }

    private fun loginAsGuest(mobile: String, hashed_value: String?) {
        addDisposable(api.loginAsGuest(mobile, hashed_value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
            if (response.result.equals("success")) {
                _navigateToActivityCall.call()
            } else {
                joinAsGuest()
            }
        }, { throwable -> throwable.printStackTrace() }))
    }

    private fun joinAsGuest() {
        addDisposable(api.joinAsGuest("post")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
            if (response.result.equals("success")) {
                val user = User(null, 0, 0, response.hashed_value, response.guest_id, response.name, null, null)
                repository.insert(user)
                _navigateToActivityCall.call()
            } else {
                // ALERT
            }
        }, { throwable -> throwable.printStackTrace() }))
    }

    private fun loginAsAuto(mobile: String, loginType: Int, hashed_value: String?) {
        addDisposable(api.loginAsAuto(mobile, loginType, hashed_value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
            if (response.result.equals("success")) {
                _navigateToActivityCall.call()
            } else {
                // ALERT
            }
        }, { throwable -> throwable.printStackTrace() }))
    }
}
