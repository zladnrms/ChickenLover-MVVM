package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class TriggerBoardLikeResponse(@SerializedName("result") var result: String? = null,
                                    @SerializedName("hashed_key") var hashed_key: String? = null,
                                    @SerializedName("last_id") var last_id: Int = 0)