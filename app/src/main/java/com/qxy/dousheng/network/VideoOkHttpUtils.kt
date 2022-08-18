package com.qxy.dousheng.network

import okhttp3.Request

class VideoOkHttpUtils {
    companion object {
        private const val baseUrl = "https://open.douyin.com/"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private const val TAG = "okHttp"
        private lateinit var videoRequest: Request

        fun getVideoRequest(): Request {
            if (!this::videoRequest.isInitialized) {
                videoRequest
            }
            return videoRequest
        }
    }
}