package com.example.dousheng

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.bytedance.sdk.open.douyin.model.OpenRecord

open class BaseAppCompatActivity : AppCompatActivity() {
    public fun httpRequest() {
        var douYinOpenApi: DouYinOpenApi? = null

        var request = OpenRecord.Request()

        val mPermissionList = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val clientKey = "awsxdh3k1fiojgnu" // 需要到开发者网站申请并替换

        DouYinOpenApiFactory.init(DouYinOpenConfig(clientKey))
    }

    public fun loadData() {

    }

}