package com.qxy.dousheng.ui.rank

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.RankAdapter
import com.qxy.dousheng.databinding.FragmentArtRankBinding
import com.qxy.dousheng.model.rank.RankItem
import com.qxy.dousheng.network.OkHttpUtils
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener

/**
 * 综艺排行榜单 Fragment
 */
class ArtRankFragment : Fragment() {
    private lateinit var binding: FragmentArtRankBinding
    private lateinit var viewModel: ArtRankViewModel

    companion object {
        fun newInstance() = ArtRankFragment()
    }

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
                    OkHttpUtils.getRankVersion()
                }
            }


            binding.spinner2.attachDataSource(OkHttpUtils.getRankVersion())

            binding.spinner2.onSpinnerItemSelectedListener =
                OnSpinnerItemSelectedListener { parent, view, position, id ->
                    Toast.makeText(requireActivity(), "成功",Toast.LENGTH_SHORT).show() }





        }
    }
}