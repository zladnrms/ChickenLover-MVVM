package defy.tech.chickenlover.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "type")
    var type: Int,

    @ColumnInfo(name = "uid")
    var uid: Int,

    @ColumnInfo(name = "hashed_value")
    var hashed_value: String?,

    @ColumnInfo(name = "guest_id")
    var guest_id: String?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "login_id")
    var login_id: String?,

    @ColumnInfo(name = "login_password")
    var login_password: String?
){
    constructor(): this(null,0, 0, null, null, null, null, null)
}