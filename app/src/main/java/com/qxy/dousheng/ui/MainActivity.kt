package com.qxy.dousheng.ui

import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.dousheng.R
import com.qxy.dousheng.database.RankDatabase
import com.qxy.dousheng.network.InfoOkHttpUtils

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init database manage &&load database
        RankDatabase.getDatabase(applicationContext)

        // Http
        httpInit()
        InfoOkHttpUtils.getAccessToken()

        // load
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottomFragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val configuration: AppBarConfiguration =
            AppBarConfiguration.Builder(bottomNavigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

}