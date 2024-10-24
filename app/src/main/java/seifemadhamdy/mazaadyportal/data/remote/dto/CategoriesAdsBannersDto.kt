package seifemadhamdy.mazaadyportal.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesAdsBannersDto(
    @SerializedName("img") var img: String? = null,
    @SerializedName("media_type") var mediaType: String? = null,
    @SerializedName("duration") var duration: Int? = null,
)
