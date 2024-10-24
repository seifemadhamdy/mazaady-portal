package seifemadhamdy.mazaadyportal.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import seifemadhamdy.mazaadyportal.ui.models.LivestreamingItemAdapterModel

class LivestreamingItemListAdapter(
    private val onItemClick: (LivestreamingItemAdapterModel) -> Unit
) :
    ListAdapter<LivestreamingItemAdapterModel, LivestreamingItemViewHolder>(
        LivestreamingItemDiffCallback()
    ) {

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
