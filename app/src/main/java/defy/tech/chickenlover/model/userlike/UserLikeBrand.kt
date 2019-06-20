package defy.tech.chickenlover.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_like_brand")
data class UserLikeBrand(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "uid")
    var uid: Int,

    @ColumnInfo(name = "brand_name")
    var brand_name: String?,

    @ColumnInfo(name = "checked")
    var checked: Boolean
){
    constructor(): this(null,0, null, false)
}