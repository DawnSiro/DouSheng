package com.example.dousheng

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ArtRankFragment : Fragment() {

    companion object {
        fun newInstance() = ArtRankFragment()
    }

    private lateinit var viewModel: ArtRankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_art_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtRankViewModel::class.java)
        // TODO: Use the ViewModel
    }

}