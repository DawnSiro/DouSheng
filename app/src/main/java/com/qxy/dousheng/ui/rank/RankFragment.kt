package com.qxy.dousheng.ui.rank

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.dousheng.R
import com.qxy.dousheng.databinding.FragmentRankBinding

class RankFragment : Fragment() {

    companion object {
        fun newInstance() = RankFragment()
    }

    private lateinit var viewModel: RankViewModel
    private lateinit var binding: FragmentRankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RankViewModel::class.java]

        // load
        val rankBottomNavigationView: BottomNavigationView =
            requireView().findViewById(R.id.rankNavigationView)
        val rankNavHostFragment: NavHostFragment =
            childFragmentManager.findFragmentById(R.id.rankFragmentContainerView) as NavHostFragment
        val navController: NavController = rankNavHostFragment.navController
        NavigationUI.setupWithNavController(rankBottomNavigationView, navController)
    }

}