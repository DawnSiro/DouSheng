package com.qxy.dousheng.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.VideoAdapter
import com.qxy.dousheng.databinding.FragmentVideoBinding
import com.qxy.dousheng.model.video.VideoItem

class VideoFragment : Fragment() {
    private lateinit var viewModel: VideoViewModel
    private lateinit var binding: FragmentVideoBinding

    companion object {
        fun newInstance() = VideoFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[VideoViewModel::class.java]

            val videoList: List<VideoItem> = if (viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            } else listOf()

            val videoAdapter = VideoAdapter(videoList)

            binding.videoRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.videoRecyclerView.adapter = videoAdapter
            binding.videoSwipeRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.videoSwipeRefreshLayout.isRefreshing = false
            }

            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    videoAdapter.videoList = it
                    videoAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}