package com.example.dousheng

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RankOfMovieFragment : Fragment() {

    companion object {
        fun newInstance() = RankOfMovieFragment()
    }

    private lateinit var viewModel: RankOfMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rank_of_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankOfMovieViewModel::class.java)
        // TODO: Use the ViewModel
    }

}