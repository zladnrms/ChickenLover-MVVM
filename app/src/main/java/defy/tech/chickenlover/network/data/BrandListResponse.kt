package defy.tech.chickenlover.network.data

import com.google.gson.annotations.SerializedName

data class BrandListResponse(@SerializedName("result") var result: String,
                             @SerializedName("result_array") var result_array : ArrayList<String> = ArrayList())