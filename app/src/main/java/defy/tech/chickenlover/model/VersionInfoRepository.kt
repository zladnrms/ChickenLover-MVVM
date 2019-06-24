package defy.tech.chickenlover.model

import android.app.Application
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import defy.tech.chickenlover.model.user.*
import io.reactivex.Maybe

class VersionInfoRepository(application: Application) {

    private val versioninfoDatabase = VersionInfoDatabase.getInstance(application)!!
    private val versioninfoDao: VersionInfoDao = versioninfoDatabase.versionInfoDao()

    fun getAll(): Maybe<List<VersionInfo>> {
        return versioninfoDao.getAll()
    }

    fun getVersionInfo(): Maybe<VersionInfo> {
        return versioninfoDao.getVersionInfo()
    }

    fun update(code: Int) {
        try {
            val thread = Thread(Runnable {
                versioninfoDao.update(code)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insert(versionInfo: VersionInfo) {
        try {
            val thread = Thread(Runnable {
                versioninfoDao.insert(versionInfo)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(versionInfo: VersionInfo) {
        try {
            val thread = Thread(Runnable {
                versioninfoDao.delete(versionInfo)
            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}