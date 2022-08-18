package com.qxy.dousheng.ui.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.qxy.dousheng.databinding.FragmentInfoBinding
import com.qxy.dousheng.network.InfoOkHttpUtils
import com.qxy.dousheng.network.OkHttpCallback

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: InfoViewModel

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
            viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

            viewModel.getLiveData().observe(requireActivity()) {
                if (it.isNotEmpty() && activity != null) {
                    val info = it[0]
                    binding.nicknameTextView.text = info.nickname

                    Glide.with(this)
                        .load(info.avatar_larger)
                        .override(900)
                        .into(binding.avatarImageView)
                    binding.cityTextView.text = "${info.country}/${info.city}"
                    binding.genderTextView.text = when (info.gender) {
                        0 -> "男"
                        else -> "女"
                    }
                    binding.descriptionTextView.text = info.description
                }
            }

            InfoOkHttpUtils.doInfoPost(object : OkHttpCallback {
                override fun isFail() {
                    Log.d("okHttp", "isFail: doInfoPost")
                }

                override fun isSuccess(json: String?) {
                    if (json == null) {
                        Log.d("okHttp", "isSuccess: json is null")
                    } else {
                        Log.d("okHttp", "isSuccess: json is ok")
                        viewModel.update(json)
                    }
                }

            })
        }
    }


}