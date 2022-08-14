package com.qxy.dousheng.ui.follow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qxy.dousheng.R
import com.qxy.dousheng.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    companion object {
        fun newInstance() = FollowFragment()
    }

    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentFollowBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}