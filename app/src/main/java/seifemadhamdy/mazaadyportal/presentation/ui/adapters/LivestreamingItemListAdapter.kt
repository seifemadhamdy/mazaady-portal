package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import seifemadhamdy.mazaadyportal.domain.model.LivestreamingItemModel

class LivestreamingItemListAdapter(
    private val onItemClick: (LivestreamingItemModel) -> Unit
) : ListAdapter<LivestreamingItemModel, LivestreamingItemViewHolder>(LivestreamingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivestreamingItemViewHolder =
        LivestreamingItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: LivestreamingItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.apply {
            itemView.setOnClickListener { onItemClick(item) }
            bind(item)
        }
    }
}