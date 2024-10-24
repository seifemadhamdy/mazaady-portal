package seifemadhamdy.mazaadyportal.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesDataDto(
    @SerializedName("categories")
    var categories: ArrayList<CategoriesCategoriesDto> = arrayListOf(),
    @SerializedName("statistics")
    var statistics: CategoriesStatisticsDto? = CategoriesStatisticsDto(),
    @SerializedName("ads_banners")
    var adsBanners: ArrayList<CategoriesAdsBannersDto> = arrayListOf(),
    @SerializedName("ios_version") var iosVersion: String? = null,
    @SerializedName("ios_latest_version") var iosLatestVersion: String? = null,
    @SerializedName("google_version") var googleVersion: String? = null,
    @SerializedName("huawei_version") var huaweiVersion: String? = null,
)
