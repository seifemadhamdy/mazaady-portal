package seifemadhamdy.mazaadyportal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import seifemadhamdy.mazaadyportal.databinding.CourseItemBinding
import seifemadhamdy.mazaadyportal.ui.models.CourseItemAdapterModel

class CourseItemViewHolder private constructor(private val binding: CourseItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CourseItemAdapterModel) {
        binding.apply {
            val context = root.context
            Glide.with(context).load(item.courseResId).into(courseImageView)

            if (!item.isLabelVisible) tagChip.visibility = View.INVISIBLE

            titleTextView.text = item.title
            timeTextView.text = item.time
            firstTag.text = item.firstTagText
            secondTag.text = item.secondTagText

            if (item.thirdTagText != null) {
                thirdTag.text = item.thirdTagText
            } else {
                thirdTag.visibility = View.INVISIBLE
            }

            Glide.with(context).load(item.avatarResId).into(courseAvatarImageView)
        }
    }

    companion object {
        fun from(parent: ViewGroup): CourseItemViewHolder =
            CourseItemViewHolder(
                CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }
}
