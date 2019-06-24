package defy.tech.chickenlover.model

import android.app.Application
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import defy.tech.chickenlover.model.user.*
import io.reactivex.Maybe

class ChickenInfoRepository(application: Application) {

    private val chickeninfoDatabase = ChickenInfoDatabase.getInstance(application)!!
    private val chickeninfoDao: ChickenInfoDao = chickeninfoDatabase.chickenInfoDao()

    fun getAll(): Maybe<List<ChickenInfo>> {
        return chickeninfoDao.getAll()
    }

    fun insert(chickenInfo: ChickenInfo) {
        try {
            val thread = Thread(Runnable {
                chickeninfoDao.insert(chickenInfo)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(chickenInfo: ChickenInfo) {
        try {
            val thread = Thread(Runnable {
                chickeninfoDao.delete(chickenInfo)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteAll() {
        try {
            val thread = Thread(Runnable {
                chickeninfoDao.deleteAll()
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}