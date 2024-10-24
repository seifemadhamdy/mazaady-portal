package seifemadhamdy.mazaadyportal.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesStatisticsDto(
    @SerializedName("auctions") var auctions: Int? = null,
    @SerializedName("users") var users: Int? = null,
    @SerializedName("products") var products: Int? = null,
)
