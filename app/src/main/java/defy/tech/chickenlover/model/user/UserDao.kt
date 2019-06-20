package defy.tech.chickenlover.model.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAll(): Maybe<List<User>>

    @Query("SELECT * FROM user WHERE id = 1")
    fun getUserInfo() : Maybe<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}