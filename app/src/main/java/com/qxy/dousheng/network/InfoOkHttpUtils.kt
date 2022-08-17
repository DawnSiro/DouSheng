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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class InfoOkHttpUtils() {
    companion object {
        private const val baseUrl = "https://open.douyin.com/"
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
                    "authCode",
                    Activity.MODE_PRIVATE
                )
                return shp.getString("authCode", "")!!
            }
            return ""
        }

        private fun getAccessRequest(): Request {
            val interfaceUrl = "/oauth/access_token/"
            val url = mockUrl + interfaceUrl
            val code = getCode()
            if (!this::accessRequest.isInitialized) {
                accessRequest = Request.Builder()
                    .url(url)
                    .addHeader("grant_type", "authorization_code")
                    .addHeader("redirect_uri", "https://www.bytedance.com/")
                    .addHeader("code", code)
                    .addHeader("client_secret", clientSecret)
                    .addHeader("client_key", clientKey)
                    .build()
            }
            Log.d(TAG, "getMovieRankRequest: $url")
            return accessRequest
        }

        @JvmName("getAccessToken1")
        private fun getAccessToken(): AccessTokenJson {
            if (this::accessToken.isInitialized) return accessToken
            accessToken = AccessTokenJson() // 默认值
            doAccessGet(object : OkHttpCallback {
                override fun isFail() {
                    Log.d(TAG, "getAccessToken 出错")
                }

                override fun isSuccess(json: String?) {
                    if (json != null && json != "{}") {
                        Log.d("okHttp", "Callback: $json")
                        accessToken = gson.fromJson(json, AccessTokenJson::class.java)
                        Log.d(TAG, "isSuccess: ${accessToken.toString()}")
                    } else {
                        Log.d(TAG, "getAccessToken 为空")
                    }
                }

            })
            return accessToken
        }

        private fun doAccessGet(callback: OkHttpCallback) {
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

        fun getInfoRequest(): Request {
            val interfaceUrl = "/oauth/userinfo/"
            val url = baseUrl + interfaceUrl
            val token = getAccessToken().data.access_token
            val id = getAccessToken().data.open_id
            val json = "{\"access_token\": $token,\"open_id\": $id}"
            Log.d(TAG, "InfoRequestJson: $json")
            val body = RequestBody.create("application/json".toMediaTypeOrNull(), json)
            if (!this::infoRequest.isInitialized) {
                infoRequest = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access-token", token)
                    .post(body)
                    .build()
            }
            return infoRequest
        }

        fun doInfoRequest(callback: OkHttpCallback) {
            infoClient.newCall(getInfoRequest()).enqueue(object : Callback {
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
    }
}