package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class WriteCommentResponse(@SerializedName("result") var result: String? = null,
                                @SerializedName("writer") var writer: String? = null,
                                @SerializedName("last_id") var last_id: Int = 0)