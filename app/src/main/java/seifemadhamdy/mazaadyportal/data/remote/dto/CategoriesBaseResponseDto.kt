package seifemadhamdy.mazaadyportal.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesBaseResponseDto(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: CategoriesDataDto? = CategoriesDataDto(),
)
