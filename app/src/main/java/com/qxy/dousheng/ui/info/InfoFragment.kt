package com.qxy.dousheng.ui.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.R
import com.qxy.dousheng.adapter.VideoAdapter
import com.qxy.dousheng.databinding.FragmentInfoBinding
import com.qxy.dousheng.model.video.VideoItem
import com.qxy.dousheng.ui.video.VideoViewModel
import com.qxy.dousheng.util.GlideUtils

/**
 * Info 用户信息模块 Fragment
 */
class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var infoViewModel: InfoViewModel
    private lateinit var videoViewModel: VideoViewModel

    companion object {
        fun newInstance() = InfoFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            infoViewModel = ViewModelProvider(this)[InfoViewModel::class.java]

            infoViewModel.getLiveData().observe(requireActivity()) {
                if (it.isNotEmpty() && activity != null) {
                    val info = it[0]
                    binding.nicknameTextView.text = info.nickname

                    // 设置图片
                    GlideUtils.loadCircle(this, info.avatar_larger, binding.avatarImageView)

                    // 设置城市
                    if (info.country == "") {
                        binding.cityTextView.text =
                            "${requireActivity().resources.getString(R.string.city)}: ${
                                requireActivity().resources.getString(
                                    R.string.china
                                )
                            }"
                    } else
                        binding.cityTextView.text =
                            "${requireActivity().resources.getString(R.string.city)}: ${info.country} ${info.province} ${info.city}"

                    // 设置性别
                    binding.genderTextView.text = when (info.gender) {
                        0 -> "${requireActivity().resources.getString(R.string.gender)}: ${
                            requireActivity().resources.getString(
                                R.string.male
                            )
                        }"
                        else -> "${requireActivity().resources.getString(R.string.gender)}: ${
                            requireActivity().resources.getString(
                                R.string.female
                            )
                        }"
                    }

                    // 设置简介
                    if (info.description == "") {
                        binding.descriptionTextView.text =
                            requireActivity().resources.getString(R.string.descriptionDefault)
                    } else {
                        binding.descriptionTextView.text = info.description
                    }
                }
            }

            infoViewModel.doInfoPost()


            // 个人视频页面
            videoViewModel = ViewModelProvider(this)[videoViewModel::class.java]

            val videoList: List<VideoItem> = if (videoViewModel.getLiveData().value != null) {
                videoViewModel.getLiveData().value!!
            } else listOf()

            val videoAdapter = VideoAdapter(videoList)

            binding.videoRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.videoRecyclerView.adapter = videoAdapter
            binding.videoSwipeRefreshLayout.setOnRefreshListener {
                videoViewModel.doGet()
                binding.videoSwipeRefreshLayout.isRefreshing = false
            }

            videoViewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    videoAdapter.videoList = it
                    videoAdapter.notifyDataSetChanged()
                }
            }


        }
    }


}