package pojo

import com.google.gson.annotations.SerializedName

data class NotFound(
    @SerializedName("detail") val detail : String
)
