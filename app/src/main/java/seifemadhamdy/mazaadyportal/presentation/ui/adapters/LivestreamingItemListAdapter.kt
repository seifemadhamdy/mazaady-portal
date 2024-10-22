package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import seifemadhamdy.mazaadyportal.domain.model.LivestreamingItemModel

class LivestreamingListAdapter(
    private val onItemClick: (LivestreamingItemModel) -> Unit
) : ListAdapter<LivestreamingItemModel, ItemViewHolder>(LivestreamingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onItemClick(item) }
        holder.bind(item)
    }
}