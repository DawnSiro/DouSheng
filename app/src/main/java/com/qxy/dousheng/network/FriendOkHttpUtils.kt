package com.qxy.dousheng.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*
import java.io.IOException

class FriendOkHttpUtils {
    companion object {
        private const val baseUrl = "https://open.douyin.com"
        private const val mockUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private val id = InfoOkHttpUtils.getAccessToken().data.open_id
        private val token = InfoOkHttpUtils.getAccessToken().data.access_token
        private const val TAG = "okHttp"
        private lateinit var followRequest: Request
        private lateinit var fansRequest: Request
        private lateinit var checkRequest: Request


        private val handle = Handler(Looper.getMainLooper())
        private val friendClient = OkHttpClient()


        // 获取关注列表请求
        private fun getFollowRequest(): Request {
            val interfaceUrl = "/following/list/"
            val count = 10
            val cursor = 0
            val url = "$baseUrl$interfaceUrl?count=$count&open_id=$id&cursor=$cursor"
            if (!this::followRequest.isInitialized) {
                followRequest = Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("access-token", token)
                    .build()
            }
            Log.d(TAG, "getFollowRequest: $url")
            return followRequest
        }

        // 获取粉丝列表请求
        private fun getFansRequest(): Request {
            val interfaceUrl = "/fans/list/"
            val count = 10
            val cursor = 0
            val url = "$baseUrl$interfaceUrl?count=$count&open_id=$id&cursor=$cursor"
            if (!this::fansRequest.isInitialized) {
                fansRequest = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access-token", token)
                    .build()
            }
            Log.d(TAG, "getFollowRequest: $url")
            return fansRequest
        }

        // 粉丝判断请求
        private fun getFansCheckRequest(checkId: String): Request {
            val interfaceUrl = "/fans/check/"

            val url = "$baseUrl$interfaceUrl?follower_open_id=$checkId&open_id=$id"
            if (!this::checkRequest.isInitialized) {
                fansRequest = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access-token", token)
                    .build()
            }
            Log.d(TAG, "getFansCheckRequest: $url")
            return fansRequest
        }

        // 获取关注列表
        fun doFollowGet(callback: OkHttpCallback) {
            friendClient.newCall(getFollowRequest()).enqueue(object : Callback {
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
        fun doFansGet(callback: OkHttpCallback) {
            friendClient.newCall(getFansRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "doFansGet: $e")
                    handle.post {
                        callback.isFail()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "doFansGet: ${response.body}")
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
        fun doFansCheckGet(checkId: String): String {
            val response = friendClient.newCall(getFansCheckRequest(checkId)).execute()
            return response.body?.string() ?: ""
        }

    }
}