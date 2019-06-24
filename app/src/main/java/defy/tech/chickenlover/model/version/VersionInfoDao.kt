package defy.tech.chickenlover.model.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface VersionInfoDao {

    @Query("SELECT * FROM version_info ORDER BY id ASC")
    fun getAll(): Maybe<List<VersionInfo>>

    @Query("SELECT * FROM version_info WHERE id = 1")
    fun getVersionInfo() : Maybe<VersionInfo>

    @Query("UPDATE version_info SET code = :code WHERE id = 1")
    fun update(code: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(versionInfo: VersionInfo)

    @Delete
    fun delete(versionInfo: VersionInfo)
}