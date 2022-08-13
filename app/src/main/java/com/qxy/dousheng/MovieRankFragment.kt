package com.qxy.dousheng

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this)[MovieRankViewModel::class.java]
            val buttonInsert: Button = requireActivity().findViewById(R.id.buttonInsert)
            val buttonClear: Button = requireActivity().findViewById(R.id.buttonClear)
            val textView: TextView = requireActivity().findViewById(R.id.textView)

            viewModel.getLiveData().observe(requireActivity()) {
                var s = ""
                for (i: MovieItem in it) {
                    s += i.id + ": " + i.name + "=" + i.hot + '\n'
                }
                textView.text = s
            }

            buttonInsert.setOnClickListener {
                val movieItem1 = MovieItem("1", "1", "1", "1", "1")
                val movieItem2 = MovieItem("2", "2", "2", "2", "2")
                val movieItem3 = MovieItem("3", "3", "3", "3", "3")
                viewModel.insertItem(movieItem1, movieItem2, movieItem3)
            }

            buttonClear.setOnClickListener {
                viewModel.clearItem()
            }
        }

    }

}