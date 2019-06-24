package defy.tech.chickenlover.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "version_info")
data class VersionInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "code")
    var code: Int?
){
    constructor(): this(null, 0)
}