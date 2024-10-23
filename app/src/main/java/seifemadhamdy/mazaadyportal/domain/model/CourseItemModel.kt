package seifemadhamdy.mazaadyportal.domain.model

import androidx.annotation.DrawableRes

data class CourseItemModel(
    val id: Int,
    @DrawableRes val courseResId: Int,
    val isLabelVisible: Boolean = true,
    val title: String,
    val time: String,
    val firstTagText: String,
    val secondTagText: String,
    val thirdTagText: String? = null,
    @DrawableRes val avatarResId: Int
)