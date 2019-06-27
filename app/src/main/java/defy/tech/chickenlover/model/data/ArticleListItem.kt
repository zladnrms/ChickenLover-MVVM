package defy.tech.chickenlover.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleListItem(var _id : Int = 0,
                           var type : Int = 0,
                           var writer : String? = null,
                           var title : String? = null,
                           var img_exist : Boolean = false,
                           var write_date : String? = null,
                           var comment_amount : Int = 0,
                           var like_amount : Int = 0) : Parcelable