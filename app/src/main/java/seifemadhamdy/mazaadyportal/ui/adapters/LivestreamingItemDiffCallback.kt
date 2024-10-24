package seifemadhamdy.mazaadyportal.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import seifemadhamdy.mazaadyportal.ui.models.LivestreamingItemAdapterModel

class LivestreamingItemDiffCallback : DiffUtil.ItemCallback<LivestreamingItemAdapterModel>() {
    override fun areItemsTheSame(
        oldItem: LivestreamingItemAdapterModel,
        newItem: LivestreamingItemAdapterModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: LivestreamingItemAdapterModel,
        newItem: LivestreamingItemAdapterModel,
    ): Boolean = oldItem == newItem
}
