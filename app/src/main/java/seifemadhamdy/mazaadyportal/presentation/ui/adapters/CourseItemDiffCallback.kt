package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import seifemadhamdy.mazaadyportal.domain.model.CourseItemModel

class CourseItemDiffCallback : DiffUtil.ItemCallback<CourseItemModel>() {
    override fun areItemsTheSame(
        oldItem: CourseItemModel, newItem: CourseItemModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CourseItemModel, newItem: CourseItemModel
    ): Boolean = oldItem == newItem
}