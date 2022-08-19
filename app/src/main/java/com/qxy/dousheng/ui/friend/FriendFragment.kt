package com.qxy.dousheng.ui.friend

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.dousheng.R

class FriendFragment : Fragment() {

    companion object {
        fun newInstance() = FriendFragment()
    }

    private lateinit var viewModel: FriendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FriendViewModel::class.java]

        // load
        val friendBottomNavigationView: BottomNavigationView =
            requireView().findViewById(R.id.friendNavigationView)
        val friendNavHostFragment: NavHostFragment =
            childFragmentManager.findFragmentById(R.id.friendFragmentContainerView) as NavHostFragment
        val navController: NavController = friendNavHostFragment.navController
        NavigationUI.setupWithNavController(friendBottomNavigationView, navController)
    }

}