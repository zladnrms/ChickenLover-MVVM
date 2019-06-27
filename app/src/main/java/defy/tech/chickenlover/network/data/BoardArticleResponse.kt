package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class BoardArticleResponse(@SerializedName("_id") var _id : Int = 0,
                                @SerializedName("type") var type : Int = 0,
                                @SerializedName("hashed_key") var hashed_key : String? = null,
                                @SerializedName("writer") var writer : String? = null,
                                @SerializedName("title") var title : String? = null,
                                @SerializedName("content") var content : String? = null,
                                @SerializedName("img_url") var img_url : ArrayList<String>?,
                                @SerializedName("write_date") var write_date : String? = null,
                                @SerializedName("comment_amount") var comment_amount : Int = 0,
                                @SerializedName("like_amount") var like_amount : Int = 0)