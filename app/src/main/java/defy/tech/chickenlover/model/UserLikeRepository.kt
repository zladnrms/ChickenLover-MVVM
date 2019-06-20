package defy.tech.chickenlover.model

import android.app.Application
import defy.tech.chickenlover.model.user.*
import io.reactivex.Maybe

class UserLikeRepository(application: Application) {

    private val userLikeDatabase = UserLikeDatabase.getInstance(application)!!
    private val userLikeDao: UserLikeDao = userLikeDatabase.userLikeDao()

    fun getLikeBrand(): Maybe<List<UserLikeBrand>> {
        return userLikeDao.getLikeBrand()
    }

    fun getLikeType(): Maybe<List<UserLikeType>> {
        return userLikeDao.getLikeType()
    }

    fun insertLikeType(userLikeType: UserLikeType) {
        try {
            val thread = Thread(Runnable {
                userLikeDao.insertLikeType(userLikeType)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertLikeBrand(userLikeBrand: UserLikeBrand) {
        try {
            val thread = Thread(Runnable {
                userLikeDao.insertLikeBrand(userLikeBrand)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}