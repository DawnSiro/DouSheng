package com.example.dousheng

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RankOfVideoFragment : Fragment() {

    companion object {
        fun newInstance() = RankOfVideoFragment()
    }

    private lateinit var viewModel: RankOfVideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rank_of_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankOfVideoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}