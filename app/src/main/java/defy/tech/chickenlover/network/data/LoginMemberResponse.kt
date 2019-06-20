package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class LoginMemberResponse(@SerializedName("result") var result: String? = null,
                               @SerializedName("hashed_value") var hashed_value : String,
                               @SerializedName("guest_id") var guest_id : String,
                               @SerializedName("name") var name : String)
