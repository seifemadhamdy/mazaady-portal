package seifemadhamdy.mazaadyportal.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import seifemadhamdy.mazaadyportal.databinding.LiveStreamingItemBinding
import seifemadhamdy.mazaadyportal.domain.model.LivestreamingItemModel

class LivestreamingItemViewHolder private constructor(
    private val binding: LiveStreamingItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LivestreamingItemModel) {
        binding.apply {
            Glide.with(avatarLiveImageView.context).load(item.avatarResId).into(avatarLiveImageView)
        }
    }

    companion object {
        fun from(parent: ViewGroup): LivestreamingItemViewHolder = LivestreamingItemViewHolder(
            LiveStreamingItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}