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
import com.qxy.dousheng.databinding.FragmentFollowBinding
import com.qxy.dousheng.model.friend.FollowItem
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.FollowOkHttpUtils

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[FollowViewModel::class.java] //.get(FollowViewModel::class.java)

            val list: List<FollowItem> = if(viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            }else{
                listOf()
            }

            val adapter = FollowAdapter(requireActivity(), list)

            // 设置 View 的 LayoutManager 和 Adapter
            val recyclerViewFollow = binding.recyclerViewFollow
            recyclerViewFollow.layoutManager = LinearLayoutManager(requireActivity())
            recyclerViewFollow.adapter = adapter

            viewModel.getLiveData().observe(requireActivity()) {
                if(activity != null){
                    adapter.followList = it
                    adapter.notifyDataSetChanged()
                }
            }


            // 发送网络请求
            FollowOkHttpUtils.doFollowGet(object : OkHttpCallback {
                private val TAG = "Follow OkHttp"
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