package com.qxy.dousheng

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clientkey = "awr4g04kxg26jk2l" // 需要到开发者网站申请

        DouYinOpenApiFactory.init(DouYinOpenConfig(clientkey))


    }

    fun douYin(): Boolean {
        val douyinOpenApi = DouYinOpenApiFactory.create(this)

        val request = Authorization.Request()
        request.scope = "user_info" // 用户授权时必选权限

        request.state = "ww";                                 // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        request.callerLocalEntry = "com.qxy.dousheng.douyinapi.DouYinEntryActivity";
        return douyinOpenApi.authorize(request) // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
    }

}