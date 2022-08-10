package com.example.dousheng

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MovieRankFragment : Fragment() {

    companion object {
        fun newInstance() = MovieRankFragment()
    }

    private lateinit var viewModel: MovieRankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieRankViewModel::class.java)
        // TODO: Use the ViewModel
    }

}