package seifemadhamdy.mazaadyportal.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import seifemadhamdy.mazaadyportal.ui.models.CourseItemAdapterModel

class CourseItemListAdapter(private val onItemClick: (CourseItemAdapterModel) -> Unit) :
    ListAdapter<CourseItemAdapterModel, CourseItemViewHolder>(CourseItemDiffCallback()) {

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
