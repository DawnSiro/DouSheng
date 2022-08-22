package com.qxy.dousheng.ui.friend

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.FriendAdapter
import com.qxy.dousheng.databinding.FragmentFollowBinding
import com.qxy.dousheng.model.friend.FriendItem

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: FollowViewModel

    companion object {
        fun newInstance() = FollowFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[FollowViewModel::class.java]

            val followList: List<FriendItem> =
                if (viewModel.getLiveData().value != null) viewModel.getLiveData().value!! else listOf()

            val followAdapter = FriendAdapter(followList)

            binding.followRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.followRecyclerView.adapter = followAdapter
            binding.followSwipeRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.followSwipeRefreshLayout.isRefreshing = false
            }

            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    followAdapter.friendList = it
                    followAdapter.notifyDataSetChanged()
                }
            }
        }
    }

}