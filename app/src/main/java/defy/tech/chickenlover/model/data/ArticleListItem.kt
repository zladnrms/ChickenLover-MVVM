package defy.tech.chickenlover.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleListItem(val _id : String,
                           val name : String,
                           val title : String,
                           var img_exist : Boolean,
                           val create_date : String,
                           var comment_amount : String) : Parcelable