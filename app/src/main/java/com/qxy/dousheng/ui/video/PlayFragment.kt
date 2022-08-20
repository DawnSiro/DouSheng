package com.qxy.dousheng.ui.video

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.qxy.dousheng.R
import com.qxy.dousheng.adapter.CallBack
import com.qxy.dousheng.databinding.FragmentPlayBinding
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.PlayOkHttpUtils
import com.qxy.dousheng.network.VideoOkHttpUtils

class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private lateinit var viewModel: PlayViewModel
    private val TAG = "PlayFragment"

    private var videoId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null){
            viewModel = ViewModelProvider(this)[PlayViewModel::class.java]

            // 获取到 videoId
            videoId = "7132411418539822373"
            //

            Log.d(TAG, "onViewCreated: 发请求前")
            PlayOkHttpUtils.doPlayUrlGet(videoId, object : OkHttpCallback {
                override fun isFail() {
                    Log.d("okHttp", "doVideoGet 出错")
                }

                override fun isSuccess(json: String?) {
                    if (json != null && json != "") {
                        Log.d("okHttp", "doVideoGet: $json")
                        viewModel.update(json)
                    } else {
                        Log.d("okHttp", "doVideoGet: json=null")
                    }
                }
            })
            Log.d(TAG, "onViewCreated: 发请求后")

            Log.d(TAG, "onViewCreated: observe 前")
            viewModel.getLiveData().observe(requireActivity()) {
                if (it.isNotEmpty() && activity != null) {
                    val info = it[0]

                    val videoView = binding.videoView

                    if(info.video_url != null){
                        Log.d(TAG, "onViewCreated: videoUrl 有参数惹")
                        videoView.setVideoURI(Uri.parse(info.video_url))
                        videoView.start()
                    }

                }
            }




        }


    }


}