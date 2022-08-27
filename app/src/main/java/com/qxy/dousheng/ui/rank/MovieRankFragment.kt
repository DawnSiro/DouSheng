package com.qxy.dousheng.ui.rank

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.RankAdapter
import com.qxy.dousheng.databinding.FragmentMovieRankBinding
import com.qxy.dousheng.model.rank.RankItem
import com.qxy.dousheng.network.OkHttpUtils

/**
 * 电影排行榜单 Fragment
 */
class MovieRankFragment : Fragment() {
    private lateinit var viewModel: MovieRankViewModel
    private lateinit var binding: FragmentMovieRankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[MovieRankViewModel::class.java]

            val list: List<RankItem> = if (viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            } else listOf()

            val adapter = RankAdapter(list)
            binding.movieRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.movieRecycler.adapter = adapter
            binding.movieRankSwipeRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.movieRankSwipeRefreshLayout.isRefreshing = false
            }
            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    adapter.rankList = it
                    adapter.notifyDataSetChanged()
                }
            }

            // 榜单版本
            val versionAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.simple_spinner_dropdown_item,
                OkHttpUtils.getRankVersion()
            )
            versionAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            binding.spinner.adapter = versionAdapter
            binding.spinner.prompt = requireActivity().resources.getString(com.qxy.dousheng.R.string.version)

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Toast.makeText(requireActivity(), "选惹", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(requireActivity(), "没有找到捏", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
}
