package com.qxy.dousheng.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.dousheng.R
import com.qxy.dousheng.network.OkHttpUtils

/**
 * 主启动 Activity 类
 */
class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 调用接口进行验权
        OkHttpUtils.getClientAccess()
        val shp = getSharedPreferences("Code", MODE_PRIVATE)
        OkHttpUtils.setContext(this)
        if (shp.getString("accessCode", "") == "") httpInit()

        // 通过 Navigation 来控制 页面切换
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