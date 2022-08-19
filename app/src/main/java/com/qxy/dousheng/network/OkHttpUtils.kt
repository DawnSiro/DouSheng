package com.qxy.dousheng.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class OkHttpUtils {
    companion object {
        private const val baseUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val TAG = "okHttp"
        private lateinit var userRequest: Request
        private lateinit var movieRequest: Request
        private lateinit var videoRequest: Request
        private lateinit var artRequest: Request
        private lateinit var followRequest: Request
        private lateinit var fansRequest: Request
        private lateinit var fansCheckRequest: Request

        private val handle = Handler(Looper.getMainLooper())
        private val rankClient = OkHttpClient()
        private val userClient = OkHttpClient()
        private val followClient = OkHttpClient()
        private val gson = Gson()


        private fun getClientAccessToken(): String {
            return "clt.55179e5b40952a805aede83e26852ce8jEBWR5yhrhv4yWYG29KKPMkAZ1kS"
        }

        fun getGson(): Gson = gson

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


        // 获取用户公开信息请求
        private fun getUserInfoRequest(): Request {
            val interfaceUrl = "/oauth/userinfo/"
            val url = baseUrl + interfaceUrl
            val accessToken = getClientAccessToken()
            if (!this::userRequest.isInitialized) {
                userRequest = Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("access-token", accessToken)
                    .build()
            }
            Log.d(TAG, "getUserInfoRequest: $url")
            return userRequest
        }

        // 获取用户公开信息
        fun doUserInfoGet(callback: OkHttpCallback) {
            userClient.newCall(getUserInfoRequest()).enqueue(object : Callback{
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

        // 获取关注列表请求
        private fun getFollowRequest() : Request {
            // https://open.douyin.com/following/list/?count=10&open_id=_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y&cursor=0
            val interfaceUrl = "/following/list/"
            val count = 10
            val open_id = "_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y"
            val cursor = 0

            val url = "$baseUrl$interfaceUrl?count=$count&open_id=$open_id&cursor=$cursor"
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

            val url = "$baseUrl$interfaceUrl?count=$count&open_id=$open_id&cursor=$cursor"
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

            val url = "$baseUrl$interfaceUrl?follower_open_id=$follower_open_id&open_id=$open_id"
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
            followClient.newCall(getMovieRankRequest()).enqueue(object : Callback {
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
