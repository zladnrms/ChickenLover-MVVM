package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class WriteCommentResponse(@SerializedName("result") var result: String? = null,
                                @SerializedName("comment_id") var comment_id: String? = null)