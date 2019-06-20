package defy.tech.chickenlover.model.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserLikeDao {

    @Query("SELECT * FROM user_like_brand ORDER BY id ASC")
    fun getLikeBrand(): Maybe<List<UserLikeBrand>>

    @Query("SELECT * FROM user_like_type ORDER BY id ASC")
    fun getLikeType(): Maybe<List<UserLikeType>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikeBrand(userLikeBrand: UserLikeBrand)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikeType(userLikeType: UserLikeType)

}