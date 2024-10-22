package seifemadhamdy.mazaadyportal.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import seifemadhamdy.mazaadyportal.R
import seifemadhamdy.mazaadyportal.databinding.FragmentHomeBinding
import seifemadhamdy.mazaadyportal.domain.model.LivestreamingItemModel
import seifemadhamdy.mazaadyportal.presentation.ui.adapters.LivestreamingListAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val livestreamingListAdapter = LivestreamingListAdapter { item ->
        // Handle item click
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val items = mutableListOf<LivestreamingItemModel>()

        for (i in 0..3) {
            items.add(LivestreamingItemModel(id = i, avatarResId = R.drawable.avatar_live))
        }

        livestreamingListAdapter.submitList(items)
    }

    private fun setupRecyclerView() {
        binding.livestreamingRecyclerView.apply {
            adapter = livestreamingListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
