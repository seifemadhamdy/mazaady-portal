package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import seifemadhamdy.mazaadyportal.domain.model.LivestreamingItemModel

class LivestreamingItemDiffCallback : DiffUtil.ItemCallback<LivestreamingItemModel>() {
    override fun areItemsTheSame(
        oldItem: LivestreamingItemModel, newItem: LivestreamingItemModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: LivestreamingItemModel, newItem: LivestreamingItemModel
    ): Boolean = oldItem == newItem
}