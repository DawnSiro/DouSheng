package com.qxy.dousheng.ui.rank

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.dousheng.adapter.RankAdapter
import com.qxy.dousheng.databinding.FragmentArtRankBinding
import com.qxy.dousheng.model.RankItem


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

    @Deprecated("Deprecated in Java")
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
            binding.artSwipRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.artSwipRefreshLayout.isRefreshing = false
            }

            viewModel.getLiveData().observe(requireActivity()) {
                if (activity != null) {
                    adapter.rankList = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}