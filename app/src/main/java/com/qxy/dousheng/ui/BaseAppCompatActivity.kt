package com.qxy.dousheng.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.dousheng.constant.AppConstant

open class BaseAppCompatActivity : AppCompatActivity() {
    // 授权的权限
    private val mScope = "trial.whitelist," +
            "user_info," +
            "discovery.ent," +
            "fans.list," +
            "following.list," +
            "fans.check," +
            "video.data," +
            "video.list"

    private var douYinOpenApi: DouYinOpenApi? = null
    private val clientKey = AppConstant.CLIENT_KEY // 需要到开发者网站申请并替换
    private val clientSecret = AppConstant.CLIENT_SECRET

    val mPermissionList = arrayOf(  //权限列表
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun httpInit(): Boolean {
        //请求
        val request = Authorization.Request()
        DouYinOpenApiFactory.init(DouYinOpenConfig(clientKey))
        var douYinOpenApi: DouYinOpenApi? = DouYinOpenApiFactory.create(this)

        request.scope = mScope // 用户授权时必选权限
        request.state = "ww" // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        douYinOpenApi?.authorize(request)
        if (douYinOpenApi != null) {
            this.douYinOpenApi = douYinOpenApi
            return true
        }
        return false
    }
}