package com.qxy.dousheng.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.qxy.dousheng.model.InfoJson
import okhttp3.*
import java.io.IOException


class VideoOkHttpUtils {
    companion object {
        private const val baseUrl = "https://open.douyin.com"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private const val TAG = "okHttp"


        private lateinit var videoRequest: Request
        private lateinit var videoJson: InfoJson

        private val handle = Handler(Looper.getMainLooper())
        private val videoClient = OkHttpClient()


        /**
         * 获取 video 请求
         * @param cursor 数据的起始游标
         * @return Request
         * * 注：由于 cursor 可能发生变化，故每次都要重新建立请求
         */
        fun getVideoRequest(cursor: Int = 0): Request {
            val interfaceUrl = "/video/list/"
            val id = InfoOkHttpUtils.getAccessToken().data.open_id
            val token = InfoOkHttpUtils.getAccessToken().data.access_token
            val count = 10  // 每页的数量
            val url = "$baseUrl$interfaceUrl?open_id=$id&cursor=$cursor&count=$count"
            Log.d(TAG, "VideoRequestUrl: $url")
            // 创建请求
            videoRequest = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build()
            return videoRequest
        }

        fun doVideoGet(callback: OkHttpCallback) {
            videoClient.newCall(getVideoRequest()).enqueue(object : Callback {
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