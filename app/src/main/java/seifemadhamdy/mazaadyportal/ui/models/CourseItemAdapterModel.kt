package seifemadhamdy.mazaadyportal.ui.models

import androidx.annotation.DrawableRes

data class CourseItemAdapterModel(
    val id: Int,
    @DrawableRes val courseResId: Int,
    val isLabelVisible: Boolean = true,
    val title: String,
    val time: String,
    val firstTagText: String,
    val secondTagText: String,
    val thirdTagText: String? = null,
    @DrawableRes val avatarResId: Int,
)
