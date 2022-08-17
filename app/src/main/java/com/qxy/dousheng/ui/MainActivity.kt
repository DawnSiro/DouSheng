package com.qxy.dousheng.ui

import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.dousheng.R
import com.qxy.dousheng.database.ItemDatabase

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        method()
        // init database manage &&load database
        ItemDatabase.getDatabase(applicationContext)

        // Http
        httpInit()

        // load bottom
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val configuration: AppBarConfiguration =
            AppBarConfiguration.Builder(bottomNavigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
//    fun method() {
//        try {
//            Thread.sleep(400L)
//        }catch (e:InterruptedException){
//            e.printStackTrace()
//        }
//    }

}