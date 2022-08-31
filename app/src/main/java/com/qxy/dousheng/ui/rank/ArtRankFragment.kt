package com.qxy.dousheng.ui.rank

import android.R
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.RankAdapter
import com.qxy.dousheng.databinding.FragmentArtRankBinding
import com.qxy.dousheng.model.rank.RankItem
import com.qxy.dousheng.network.OkHttpUtils

/**
 * 综艺排行榜单 Fragment
 */
class ArtRankFragment : Fragment() {
    private lateinit var binding: FragmentArtRankBinding
    private lateinit var viewModel: ArtRankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[ArtRankViewModel::class.java]

            val list: List<RankItem> = if (viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            } else listOf()

            val adapter = RankAdapter(list)

            binding.artRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.artRecycler.adapter = adapter
            binding.artRankSwipeRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.artRankSwipeRefreshLayout.isRefreshing = false
            }

            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    adapter.rankList = it
                    adapter.notifyDataSetChanged()
                }
            }

            // 榜单版本
            val versionList: List<String> = OkHttpUtils.getRankVersion()
            Log.d("TAG", "onActivityCreated: $versionList")
            // 榜单版本
            binding.versionTextview.setOnClickListener{
                showSingDialog(binding.versionTextview, versionList.toTypedArray())
            }

        }
    }



    var choice = 0
    @SuppressLint("SetTextI18n")
    private fun showSingDialog(versionView: TextView, versionArray: Array<CharSequence>) {
        val items: Array<CharSequence> = listOf("版本1", "版本2", "版本3",
            "版本4", "版本5", "版本6",
            "版本7" ,"版本8", "版本9",
            "版本10" ,"版本11", "版本12",
            "版本13" ,"版本14", "版本15",
            "版本16" ,"版本17", "版本18").toTypedArray()
        val singleChoiceDialog = android.app.AlertDialog.Builder(requireActivity())
        Log.d("TAG", "showSingDialog: $versionArray")
        singleChoiceDialog.setTitle("单选Dialog 实现版本选择")
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(versionArray, choice) { _, which ->
            choice = which
            viewModel.version = versionArray[choice].split(":")[0].toInt()
        }
        singleChoiceDialog.setPositiveButton("确定",
            DialogInterface.OnClickListener { _, _ ->
                if (viewModel.version != -1) {
                    Toast.makeText(
                        requireActivity(),
                        "你选择了" + versionArray[choice],
                        Toast.LENGTH_SHORT
                    ).show()
                    versionView.text = versionArray[choice]
                }
                Log.d("TAG", "showSingDialog: ${viewModel.version}")
            })
        singleChoiceDialog.show()
        println("执行完毕")
    }


}