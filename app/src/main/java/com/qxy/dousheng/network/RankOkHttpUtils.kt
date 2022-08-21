package com.qxy.dousheng.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*
import java.io.IOException

class RankOkHttpUtils {
    companion object {
        private const val baseUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val TAG = "okHttp"
        private lateinit var movieRequest: Request
        private lateinit var videoRequest: Request
        private lateinit var artRequest: Request

        private val handle = Handler(Looper.getMainLooper())
        private val rankClient = OkHttpClient()


        private fun getClientAccessToken(): String {
            return "clt.55179e5b40952a805aede83e26852ce8jEBWR5yhrhv4yWYG29KKPMkAZ1kS"
        }


        private fun getMovieRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=1"
            val url = baseUrl + interfaceUrl
            val accessToken = getClientAccessToken()
            if (!this::movieRequest.isInitialized) {
                movieRequest = Request.Builder()
                    .url(url)
//                    .header("access-token", getClientAccessToken())
                    .build()
            }
            Log.d(TAG, "getMovieRankRequest: $url")
            return movieRequest
        }

        private fun getVideoRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=2"
            val url = baseUrl + interfaceUrl
            val accessToken = getClientAccessToken()
            if (!this::videoRequest.isInitialized) {
                videoRequest = Request.Builder()
                    .url(url)
//                    .header("access-token", getClientAccessToken())
                    .build()
            }
            Log.d(TAG, "getVideoRankRequest: $url")
            return videoRequest
        }

        private fun getArtRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=3"
            val url = baseUrl + interfaceUrl
            val accessToken = getClientAccessToken()
            if (!this::artRequest.isInitialized) {
                artRequest = Request.Builder()
                    .url(url)
//                    .header("access-token", getClientAccessToken())
                    .build()
            }
            Log.d(TAG, "getArtRankRequest: $url")
            return artRequest
        }

        fun doMovieGet(callback: OkHttpCallback) {
            rankClient.newCall(getMovieRankRequest()).enqueue(object : Callback {
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

        fun doVideoGet(callback: OkHttpCallback) {
            rankClient.newCall(getVideoRankRequest()).enqueue(object : Callback {
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

        fun doArtGet(callback: OkHttpCallback) {
            rankClient.newCall(getArtRankRequest()).enqueue(object : Callback {
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
