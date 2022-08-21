package com.qxy.dousheng.ui.rank

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.RankAdapter
import com.qxy.dousheng.databinding.FragmentVideoRankBinding
import com.qxy.dousheng.model.RankItem

class VideoRankFragment : Fragment() {
    private lateinit var binding: FragmentVideoRankBinding
    private lateinit var viewModel: VideoRankViewModel

    companion object {
        fun newInstance() = VideoRankFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[VideoRankViewModel::class.java]

            val list: List<RankItem> = if (viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            } else listOf()

            val adapter = RankAdapter(list)

            binding.videoRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.videoRecycler.adapter = adapter
            binding.videoRankSwipeRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.videoRankSwipeRefreshLayout.isRefreshing = false
            }

            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    adapter.rankList = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}