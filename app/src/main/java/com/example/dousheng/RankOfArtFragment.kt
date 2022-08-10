package com.example.dousheng

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RankOfArtFragment : Fragment() {

    companion object {
        fun newInstance() = RankOfArtFragment()
    }

    private lateinit var viewModel: RankOfArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rank_of_art, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankOfArtViewModel::class.java)
        // TODO: Use the ViewModel
    }

}