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
import com.qxy.dousheng.databinding.FragmentFansBinding
import com.qxy.dousheng.model.friend.FriendItem

/**
 * 粉丝列表 Fragment
 */
class FansFragment : Fragment() {
    private lateinit var binding: FragmentFansBinding
    private lateinit var viewModel: FansViewModel

    companion object {
        fun newInstance() = FansFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFansBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[FansViewModel::class.java]

            val fansList: List<FriendItem> =
                if (viewModel.getLiveData().value != null) viewModel.getLiveData().value!! else listOf()

            val fansAdapter = FriendAdapter(fansList)

            binding.fansRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.fansRecyclerView.adapter = fansAdapter
            binding.fansSwipeRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.fansSwipeRefreshLayout.isRefreshing = false
            }

            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    fansAdapter.friendList = it
                    fansAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}