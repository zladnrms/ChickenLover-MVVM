package defy.tech.chickenlover.model

import android.app.Application
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import defy.tech.chickenlover.model.user.*
import io.reactivex.Maybe

class UserRepository(application: Application) {

    private val userDatabase = UserDatabase.getInstance(application)!!
    private val userDao: UserDao = userDatabase.userDao()

    fun getAll(): Maybe<List<User>> {
        return userDao.getAll()
    }

    fun getUserInfo(): Maybe<User> {
        return userDao.getUserInfo()
    }

    fun insert(user: User) {
        try {
            val thread = Thread(Runnable {
                userDao.insert(user)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(user: User) {
        try {
            val thread = Thread(Runnable {
                userDao.delete(user)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}