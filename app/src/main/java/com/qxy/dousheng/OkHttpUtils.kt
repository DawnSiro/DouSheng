package com.qxy.dousheng

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
        private lateinit var rankRequest: Request
        private val handle = Handler(Looper.getMainLooper())
        private val rankClient = OkHttpClient()
        private val gson = Gson()


        private fun getClientAccessToken(): String {
            return "clt.55179e5b40952a805aede83e26852ce8jEBWR5yhrhv4yWYG29KKPMkAZ1kS"
        }

        fun getGson(): Gson = gson

        private fun getMovieRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=1"
            val url = baseUrl + interfaceUrl
            val accessToken = getClientAccessToken()
            if (!this::rankRequest.isInitialized) {
                rankRequest = Request.Builder()
                    .url(url)
//                    .header("access-token", getClientAccessToken())
                    .build()
            }
            Log.d(TAG, "getMovieRankRequest: $url")
            return rankRequest
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

    }
}
