package com.qxy.dousheng

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieRankFragment : Fragment() {
    private lateinit var viewModel: MovieRankViewModel

    companion object {
        fun newInstance() = MovieRankFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_rank, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[MovieRankViewModel::class.java]
            val buttonInsert: Button = requireActivity().findViewById(R.id.buttonInsert)
            val buttonClear: Button = requireActivity().findViewById(R.id.buttonClear)

            val list: List<MovieItem> = if (viewModel.getLiveData().value != null) {
                viewModel.getLiveData().value!!
            } else listOf()
            val adapter = RankAdapter(list)
            val recyclerView: RecyclerView = requireView().findViewById(R.id.movie_recycler)

            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter

            viewModel.getLiveData().observe(requireActivity()) {
                adapter.rankList = it
                adapter.notifyDataSetChanged()
            }

            buttonInsert.setOnClickListener {
                for (i in 1..30) {
                    val movieItem = MovieItem(
                        i.toLong(),
                        i.toString(),
                        i.toString(),
                        i.toString(),
                        i.toLong()
                    )
                    viewModel.insertItem(movieItem)

                }
            }

            buttonClear.setOnClickListener {
                viewModel.clearItem()
            }
        }

    }

}