package seifemadhamdy.mazaadyportal.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesChildrenDto(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("children") var children: String? = null,
    @SerializedName("circle_icon") var circleIcon: String? = null,
    @SerializedName("disable_shipping") var disableShipping: Int? = null,
)
