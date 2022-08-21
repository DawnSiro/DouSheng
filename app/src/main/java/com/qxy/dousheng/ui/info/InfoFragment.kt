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
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.qxy.dousheng.R
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

                    // 设置图片
                    val options: RequestOptions = RequestOptions().transform(CircleCrop())
                    Glide.with(this)
                        .load(info.avatar_larger)
                        .apply(options)
                        .into(binding.avatarImageView)

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
                    } else binding.descriptionTextView.text = info.description
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