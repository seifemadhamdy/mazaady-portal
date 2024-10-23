package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import seifemadhamdy.mazaadyportal.domain.model.CourseItemModel

class CourseItemListAdapter(
    private val onItemClick: (CourseItemModel) -> Unit
) : ListAdapter<CourseItemModel, CourseItemViewHolder>(CourseItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseItemViewHolder =
        CourseItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: CourseItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.apply {
            itemView.setOnClickListener { onItemClick(item) }
            bind(item)
        }
    }
}