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
import com.qxy.dousheng.databinding.FragmentMovieRankBinding
import com.qxy.dousheng.model.RankItem

class MovieRankFragment : Fragment() {
    private lateinit var viewModel: MovieRankViewModel
    private lateinit var binding: FragmentMovieRankBinding

    companion object {
        fun newInstance() = MovieRankFragment()
    }


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
            binding.movieRankSwipRefreshLayout.setOnRefreshListener {
                viewModel.doGet()
                binding.movieRankSwipRefreshLayout.isRefreshing = false
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
