package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import seifemadhamdy.mazaadyportal.databinding.LiveStreamingItemBinding
import seifemadhamdy.mazaadyportal.domain.model.LivestreamingItemModel

class ItemViewHolder private constructor(
    private val binding: LiveStreamingItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LivestreamingItemModel) {
        binding.apply {
            avatarLiveImageView.setImageResource(item.avatarResId)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LiveStreamingItemBinding.inflate(layoutInflater, parent, false)
            return ItemViewHolder(binding)
        }
    }
}