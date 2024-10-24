package seifemadhamdy.mazaadyportal.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import seifemadhamdy.mazaadyportal.R
import seifemadhamdy.mazaadyportal.databinding.FragmentHomeBinding
import seifemadhamdy.mazaadyportal.ui.adapters.CourseItemListAdapter
import seifemadhamdy.mazaadyportal.ui.adapters.LivestreamingItemListAdapter
import seifemadhamdy.mazaadyportal.ui.models.CourseItemAdapterModel
import seifemadhamdy.mazaadyportal.ui.models.LivestreamingItemAdapterModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val livestreamingItemListAdapter = LivestreamingItemListAdapter { item ->
        Toast.makeText(context, "Item #${item.id} clicked.", Toast.LENGTH_SHORT).show()
    }

    private val courseItemListAdapter = CourseItemListAdapter { item ->
        Toast.makeText(context, "Item #${item.id} clicked.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLivestreamingRecyclerView()
        populateLivestreamingRecyclerView()
        setupCourseRecyclerView()
        populateCourseRecyclerView()
        binding.scrollingPagerIndicator.attachToRecyclerView(binding.courseRecyclerView)
    }

    private fun setupLivestreamingRecyclerView() {
        binding.livestreamingRecyclerView.apply {
            adapter = livestreamingItemListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun populateLivestreamingRecyclerView() {
        livestreamingItemListAdapter.submitList(
            mutableListOf<LivestreamingItemAdapterModel>().apply {
                add(LivestreamingItemAdapterModel(id = 1, avatarResId = R.drawable.avatar_live_1))
                add(LivestreamingItemAdapterModel(id = 2, avatarResId = R.drawable.avatar_live_2))
                add(LivestreamingItemAdapterModel(id = 3, avatarResId = R.drawable.avatar_live_3))
                add(LivestreamingItemAdapterModel(id = 4, avatarResId = R.drawable.avatar_live_4))
            }
        )
    }

    private fun setupCourseRecyclerView() {
        binding.courseRecyclerView.apply {
            adapter = courseItemListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun populateCourseRecyclerView() {
        courseItemListAdapter.submitList(
            mutableListOf<CourseItemAdapterModel>().apply {
                add(
                    CourseItemAdapterModel(
                        id = 1,
                        courseResId = R.drawable.course_background_1,
                        title = resources.getString(R.string.step_design_sprint_for_nbeginner),
                        time = resources.getString(R.string._5h_21m),
                        firstTagText = resources.getString(R.string._6_lessons),
                        secondTagText = resources.getString(R.string.ui_ux),
                        thirdTagText = resources.getString(R.string.free),
                        avatarResId = R.drawable.avatar_course_1,
                    )
                )
                add(
                    CourseItemAdapterModel(
                        id = 2,
                        courseResId = R.drawable.course_background_2,
                        isLabelVisible = false,
                        title = resources.getString(R.string.basic_skill_for_sketch_nillustration),
                        time = resources.getString(R.string._3h_21m),
                        firstTagText = resources.getString(R.string._2_lessons),
                        secondTagText = resources.getString(R.string.design),
                        avatarResId = R.drawable.avatar_course_2,
                    )
                )
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
