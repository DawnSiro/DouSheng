package com.qxy.dousheng.ui.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.qxy.dousheng.R
import com.qxy.dousheng.databinding.FragmentFriendBinding

/**
 * Friend 朋友模块页面 Fragment
 */
class FriendFragment : Fragment() {
    private lateinit var binding: FragmentFriendBinding
    private lateinit var viewModel: FriendViewModel

    companion object {
        fun newInstance() = FriendFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FriendViewModel::class.java]

        // load
        val friendNavHostFragment: NavHostFragment =
            childFragmentManager.findFragmentById(R.id.friendFragmentContainerView) as NavHostFragment
        val navController: NavController = friendNavHostFragment.navController
        NavigationUI.setupWithNavController(binding.friendBottomNavigationView, navController)
    }

}