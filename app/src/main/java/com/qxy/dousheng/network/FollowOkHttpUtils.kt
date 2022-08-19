package com.qxy.dousheng.network

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class FollowOkHttpUtils {
    companion object{
        private const val douYinUrl = "https://open.douyin.com"
        private const val mockUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val TAG = "okHttp"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private lateinit var accessRequest: Request
        private lateinit var followRequest: Request
        private lateinit var fansRequest: Request
        private lateinit var fansCheckRequest: Request

        private val handle = Handler(Looper.getMainLooper())
        private val followClient = OkHttpClient()

        private fun getClientAccessToken(): String {
            return "act.22c0e318d8c6ee464b2d00bc45a4ed00J4k8tmaes4K1DYsrTOT0AV7uXw3M"
        }

        // 获取关注列表请求
        private fun getFollowRequest() : Request {
            // https://open.douyin.com/following/list/?count=10&open_id=_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y&cursor=0
            val interfaceUrl = "/following/list/"
            val count = 10
            val open_id = "_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y"
            val cursor = 0

            val url = "$mockUrl$interfaceUrl?count=$count&open_id=$open_id&cursor=$cursor"
            val accessToken = getClientAccessToken()
            if (!this::followRequest.isInitialized) {
                followRequest = Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .build()
            }
            Log.d(TAG, "getFollowRequest: $url")
            return followRequest
        }



        // 获取粉丝列表请求
        private fun getFansRequest() : Request {
            // https://open.douyin.com/following/list/?count=10&open_id=_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y&cursor=0
            val interfaceUrl = "/fans/list/"
            val count = 10
            val open_id = "_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y"
            val cursor = 0

            val url = "$mockUrl$interfaceUrl?count=$count&open_id=$open_id&cursor=$cursor"
            val accessToken = getClientAccessToken()
            if (!this::fansRequest.isInitialized) {
                fansRequest = Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .build()
            }
            Log.d(TAG, "getFollowRequest: $url")
            return fansRequest
        }

        // 粉丝判断请求
        private fun getFansCheckRequest() : Request {
            // https://open.douyin.com/fans/check/?follower_open_id=_0004qbdNBa3P3W4AQ1Tf0U-NIMA5b0jQhuB&open_id=_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y
            val interfaceUrl = "/fans/check/"
            val follower_open_id = "_0004qbdNBa3P3W4AQ1Tf0U-NIMA5b0jQhuB"
            val open_id = "_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y"

            val url = "$mockUrl$interfaceUrl?follower_open_id=$follower_open_id&open_id=$open_id"
            val accessToken = getClientAccessToken()
            if (!this::fansRequest.isInitialized) {
                fansRequest = Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .build()
            }
            Log.d(TAG, "getFollowRequest: $url")
            return fansRequest
        }


        // 获取关注列表
        fun doFollowGet(callback: OkHttpCallback){
            followClient.newCall(getFollowRequest()).enqueue(object : Callback {
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

        // 获取粉丝列表
        fun doFansGet(callback: OkHttpCallback){
            followClient.newCall(getFansRequest()).enqueue(object : Callback {
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

        // 判断是否为粉丝
        fun doFansCheckGet(callback: OkHttpCallback){
            followClient.newCall(getFollowRequest()).enqueue(object : Callback {
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