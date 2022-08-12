package com.qxy.dousheng

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clientkey = "awr4g04kxg26jk2l" // 需要到开发者网站申请

        DouYinOpenApiFactory.init(DouYinOpenConfig(clientkey))

    }
}