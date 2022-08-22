package com.qxy.dousheng.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.qxy.dousheng.model.ClientAccessJson
import com.qxy.dousheng.model.rank.RankVersionJson
import okhttp3.*
import java.io.IOException

class RankOkHttpUtils {
    companion object {
        private const val baseUrl = "https://open.douyin.com"
        private const val mockUrl = "https://mock.apifox.cn/m1/1441581-0-default"
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private const val TAG = "okHttp"
        private lateinit var movieRequest: Request
        private lateinit var videoRequest: Request
        private lateinit var artRequest: Request
        private lateinit var rankVersionRequest: Request
        private lateinit var ClientAccessRequest: Request

        private val handle = Handler(Looper.getMainLooper())
        private val rankClient = OkHttpClient()
        private val ClientAccessClient = OkHttpClient()
        private val gson = Gson()
        var clientAccess: String = ""


        private fun getClientAccessRequest(): Request {
            val interfaceUrl = "/oauth/client_token/"
            val url = baseUrl + interfaceUrl
            val body = FormBody.Builder()
                .addEncoded("client_key", clientKey)
                .addEncoded("client_secret", clientSecret)
                .addEncoded("grant_type", "client_credential")
                .build()
            if (!this::ClientAccessRequest.isInitialized) {
                ClientAccessRequest = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "multipart/form-data")
                    .post(body)
                    .build()
            }
            Log.d(TAG, "getClientAccessRequest: $url")
            return ClientAccessRequest
        }

        private fun doClientAccessGet(callback: OkHttpCallback) {
            ClientAccessClient.newCall(getClientAccessRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "doClientAccessGet: $e")
                    handle.post {
                        callback.isFail()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "doClientAccessGet: ${response.body}")
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

        fun getClientAccess() {
            doClientAccessGet(object : OkHttpCallback {
                override fun isFail() {
                    Log.d(TAG, "isFail: doClientAccessGet")
                }

                override fun isSuccess(json: String?) {
                    if (json == null || json == "") {
                        Log.d("okHttp", "getClientAccess: json is null")
                    } else {
                        Log.d("okHttp", "getClientAccess: $json")
                        val clientAccessJson = gson.fromJson(json, ClientAccessJson::class.java)
                        clientAccess = clientAccessJson.data.access_token
                        Log.d("okHttp", "getClientAccess: ${clientAccessJson.data.access_token}")
                    }
                }
            })
        }


        private fun getMovieRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=1"
            val url = baseUrl + interfaceUrl
            if (!this::movieRequest.isInitialized) {
                movieRequest = Request.Builder()
                    .url(url)
                    .addHeader("access-token", clientAccess)
                    .build()
            }
            Log.d(TAG, "getMovieRankRequest: $url")
            return movieRequest
        }

        private fun getVideoRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=2"
            val url = baseUrl + interfaceUrl

            videoRequest = Request.Builder()
                .url(url)
                .addHeader("access-token", clientAccess)
                .build()

            Log.d("okHttp", "getClientAccess: $clientAccess")
            Log.d(TAG, "getVideoRankRequest: $url")
            return videoRequest
        }

        private fun getArtRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=3"
            val url = baseUrl + interfaceUrl

            if (!this::artRequest.isInitialized) {
                artRequest = Request.Builder()
                    .url(url)
                    .addHeader("access-token", clientAccess)
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

        private fun getRankVersionRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/version/"
            var url = "$baseUrl$interfaceUrl?cursor=0&count=10&type=1"
            if (!this::rankVersionRequest.isInitialized) {
                rankVersionRequest = Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access-token", clientAccess)
                    .url(url)
                    .build()
            }
            return rankVersionRequest
        }

        fun doRankVersionGet(callback: OkHttpCallback) {
            rankClient.newCall(getRankVersionRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "doRankVersionGet: $e")
                    handle.post {
                        callback.isFail()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "doRankVersionGet: ${response.body}")
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

        fun getRankVersion(): List<String> {
            val rankVersionData: ArrayList<String> = arrayListOf()

            doRankVersionGet(object : OkHttpCallback {
                override fun isFail() {
                    Log.d("okHttp", "isFail: doInfoPost")
                }

                override fun isSuccess(json: String?) {
                    if (json == null || json == "") {
                        Log.d("okHttp", "getRankVersion: json is null")
                    } else {
                        Log.d("okHttp", "getRankVersion: $json")
                        val rankVersionJson = gson.fromJson(json, RankVersionJson::class.java)

                        for (i in rankVersionJson.data.list) {
                            rankVersionData.add("${i.version}: ${i.start_time}")
                        }

                    }
                }

            })
            return rankVersionData
        }
    }
}
