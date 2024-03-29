package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class BoardCommentResponse(
    @SerializedName("result") var result: String? = null,
    @SerializedName("result_array") var result_array : ArrayList<String> = ArrayList())
