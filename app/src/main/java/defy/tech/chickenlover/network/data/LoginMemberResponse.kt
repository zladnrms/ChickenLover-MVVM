package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class LoginMemberResponse(@SerializedName("result") var result: String? = null,
                               @SerializedName("hashed_key") var hashed_value : String? = null,
                               @SerializedName("type") var type: Int = 0,
                               @SerializedName("name") var name : String? = null)
