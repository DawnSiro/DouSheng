package com.qxy.dousheng.ui.friend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.FollowAdapter
import com.qxy.dousheng.databinding.FragmentFansBinding
import com.qxy.dousheng.model.FollowItem
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.FollowOkHttpUtils

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FansFragment : Fragment() {

    private var _binding: FragmentFansBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: FansViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFansBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[FansViewModel::class.java] //.get(FollowViewModel::class.java)

            val list: List<FollowItem> = if(viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            }else{
                listOf()
            }

            val adapter = FollowAdapter(requireActivity(), list)

            // 设置 View 的 LayoutManager 和 Adapter
            val recyclerViewFollow = binding.recyclerViewFans
            recyclerViewFollow.layoutManager = LinearLayoutManager(requireActivity())
            recyclerViewFollow.adapter = adapter

            viewModel.getLiveData().observe(requireActivity()) {
                adapter.followList = it
                adapter.notifyDataSetChanged()
            }


            // 发送网络请求
            FollowOkHttpUtils.doFansGet(object : OkHttpCallback {
                private val TAG = "Fans OkHttp"
                override fun isFail() {
                    Log.d(TAG, "isFail: Callback 出错")
                    Toast.makeText(requireActivity(), "Follow OkHttp Callback 出错",
                        Toast.LENGTH_SHORT)
                        .show()
                }

                override fun isSuccess(json: String?) {
                    if(json != null) {
                        Log.d(TAG, "isSuccess: $json")
                        viewModel.update(json) // 如果有 json 数据，更新 ViewModel
                    }else{
                        Toast.makeText(requireActivity(), "Response Body 为空", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}