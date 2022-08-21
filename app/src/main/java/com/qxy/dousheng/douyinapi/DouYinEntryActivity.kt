package com.qxy.dousheng.douyinapi

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import com.bytedance.sdk.open.aweme.CommonConstants
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler
import com.bytedance.sdk.open.aweme.common.model.BaseReq
import com.bytedance.sdk.open.aweme.common.model.BaseResp
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.dousheng.network.InfoOkHttpUtils

/**
 * 主要功能：接受授权返回结果的activity
 * 也可通过request.callerLocalEntry = "com.xxx.xxx...activity"; 定义自己的回调类
 */
class DouYinEntryActivity : Activity(), IApiEventHandler {
    lateinit var douYinOpenApi: DouYinOpenApi
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(intent, this);
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {
        // 授权成功可以获得authCode
        if (resp.type == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            val response = resp as Authorization.Response
            if (resp.isSuccess()) {
                Log.d("authCode", response.authCode)

                // 持久化
                val shp: SharedPreferences = getSharedPreferences("authCode", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = shp.edit()
                editor.putString("authCode", response.authCode)
                editor.apply()
                Toast.makeText(
                    this, "授权成功，获得权限：" + response.grantedPermissions,
                    Toast.LENGTH_LONG
                ).show()

                InfoOkHttpUtils.setContext(this)
                InfoOkHttpUtils.getAccessToken()

            } else {
                Toast.makeText(
                    this, "授权失败" + response.grantedPermissions,
                    Toast.LENGTH_LONG
                ).show()
            }
            finish()
        }
    }

    override fun onErrorIntent(@Nullable intent: Intent?) {
        // 错误数据
        Toast.makeText(this, "intent出错啦", Toast.LENGTH_LONG).show()
        finish()

    }
}