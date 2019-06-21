package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class WriteArticleResponse(@SerializedName("result") var result: String? = null,
                                @SerializedName("last_id") var last_id: Int = 0)