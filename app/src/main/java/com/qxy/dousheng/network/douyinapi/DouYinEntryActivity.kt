package com.qxy.dousheng.network.douyinapi

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
import com.qxy.dousheng.network.OkHttpUtils
import com.qxy.dousheng.network.OkHttpCallback

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
                val shp: SharedPreferences = getSharedPreferences("Code", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = shp.edit()
                editor.putString("authCode", response.authCode)
                editor.apply()
                Toast.makeText(
                    this, "授权成功，获得权限：" + response.grantedPermissions,
                    Toast.LENGTH_LONG
                ).show()

                OkHttpUtils.setContext(this)
                OkHttpUtils.doAccessGet(object : OkHttpCallback {
                    override fun isFail() {
                        Log.d("okHttp", "doAccessGet 出错")
                    }

                    override fun isSuccess(json: String?) {
                        if (json != null && json != "{}") {
                            Log.d("okHttp", "doAccessGet: $json")
                            editor.putString("accessCode", json)
                            editor.apply()
                        } else {
                            Log.d("okHttp", "doAccessGet 为空")
                        }
                    }
                })

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