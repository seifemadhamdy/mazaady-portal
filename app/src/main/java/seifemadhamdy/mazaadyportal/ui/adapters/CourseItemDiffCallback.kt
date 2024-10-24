package seifemadhamdy.mazaadyportal.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import seifemadhamdy.mazaadyportal.ui.models.CourseItemAdapterModel

class CourseItemDiffCallback : DiffUtil.ItemCallback<CourseItemAdapterModel>() {
    override fun areItemsTheSame(
        oldItem: CourseItemAdapterModel,
        newItem: CourseItemAdapterModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CourseItemAdapterModel,
        newItem: CourseItemAdapterModel,
    ): Boolean = oldItem == newItem
}
