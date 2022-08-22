package com.qxy.dousheng.network

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.qxy.dousheng.model.AccessTokenJson
import okhttp3.*
import java.io.IOException

class InfoOkHttpUtils() {
    companion object {
        private const val baseUrl = "https://open.douyin.com"
        private const val mockUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private const val TAG = "okHttp"
        private lateinit var infoRequest: Request
        private lateinit var accessRequest: Request
        private lateinit var accessToken: AccessTokenJson


        private val handle = Handler(Looper.getMainLooper())
        private val infoClient = OkHttpClient()
        private val gson = Gson()


        // 上下文
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        // 设定上下文
        fun setContext(context: Context) {
            this.context = context
        }

        // 从 SharedPreferences 读取 code
        private fun getCode(): String {
            if (this::context.isInitialized) {
                val shp: SharedPreferences = context.getSharedPreferences(
                    "Code",
                    Activity.MODE_PRIVATE
                )
                return shp.getString("authCode", "")!!
            }
            return ""
        }

        private fun getAccessRequest(): Request {
            val interfaceUrl = "/oauth/access_token/"
            val url =
                "$baseUrl$interfaceUrl?grant_type=authorization_code&redirect_uri=https://www.bytedance.com/&client_secret=$clientSecret&client_key=$clientKey&code=${getCode()}"
            if (!this::accessRequest.isInitialized) {
                accessRequest = Request.Builder()
                    .url(url)
                    .build()
            }
            Log.d(TAG, "getAccessRequest: $url")
            return accessRequest
        }

        @JvmName("getAccessToken1")
        fun getAccessToken(): AccessTokenJson {
            if (!this::accessToken.isInitialized) {
                accessToken = AccessTokenJson() // 默认值

                val shp: SharedPreferences = context.getSharedPreferences(
                    "Code",
                    Activity.MODE_PRIVATE
                )
                val accessTokenJson: String = shp.getString("accessCode", "")!!

                if (accessTokenJson != "")
                    accessToken = gson.fromJson(accessTokenJson, AccessTokenJson::class.java)
            }
            return accessToken
        }

        fun doAccessGet(callback: OkHttpCallback) {
            infoClient.newCall(getAccessRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "onFailure: $e")
                    handle.post {
                        callback.isFail()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "onResponse: ${response.body}")
                    val json = response.body?.string()
                    if (json != null) {
                        handle.post {
                            callback.isSuccess(json)
                        }
                    } else {
                        handle.post {
                            callback.isFail()
                        }
                    }
                }

            })
        }

        // 因为 token 和 id 可能获取失败，所以无法采用单例模式，每次都要重新获取新的 request
        private fun getInfoRequest(): Request {
            val interfaceUrl = "/oauth/userinfo/"
            val url = baseUrl + interfaceUrl
//            if (!this::infoRequest.isInitialized) {
            // 初始化耗时参数
            val token = getAccessToken().data.access_token
            val id = getAccessToken().data.open_id
            val body = FormBody
                .Builder()
                .addEncoded("access_token", token)
                .addEncoded("open_id", id)
                .build()
            Log.d(TAG, "token:$token id:$id")


            // 初始化 infoRequest
            infoRequest = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .post(body)
                .build()
//            }
            return infoRequest
        }

        fun doInfoPost(callback: OkHttpCallback) {
            infoClient.newCall(getInfoRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "doInfoPost: $e")
                    handle.post {
                        callback.isFail()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "doInfoPost: ${response.body}")
                    val json = response.body?.string()
                    if (json != null) {
                        handle.post {
                            callback.isSuccess(json)
                        }
                    } else {
                        handle.post {
                            callback.isFail()
                        }
                    }
                }

            })

        }
    }
}