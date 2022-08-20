package com.qxy.dousheng.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*
import java.io.IOException

class PlayOkHttpUtils {
    companion object{
        private const val douYinUrl = "https://open.douyin.com"
        private const val mockUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val TAG = "okHttp"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private lateinit var playRequest: Request

        private val handle = Handler(Looper.getMainLooper())
        private val playClient = OkHttpClient()

        private fun getClientAccessToken(): String {
            return "act.22c0e318d8c6ee464b2d00bc45a4ed00J4k8tmaes4K1DYsrTOT0AV7uXw3M"
        }

        // 通过 videoId 标识要获取的视频，目前用的是 mock
        private fun getPlayRequest(videoId: String) : Request {
            // https://mock.apifox.cn/m1/1441581-0-default/video/feed?video_id=7132411418539822373
            val interfaceUrl = "/video/feed"

            val url = "$mockUrl$interfaceUrl?video_id=$videoId"
            val accessToken = getClientAccessToken()
            if (!this::playRequest.isInitialized) {
                playRequest = Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .build()
            }
            Log.d(TAG, "getPlayRequest: $url")
            return playRequest
        }

        // 获取视频 Url
        fun doPlayUrlGet(videoId: String, callback: OkHttpCallback) {
            playClient.newCall(getPlayRequest(videoId)).enqueue(object : Callback {
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