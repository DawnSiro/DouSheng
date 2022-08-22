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
import com.qxy.dousheng.model.ClientAccessJson
import com.qxy.dousheng.model.rank.RankVersionJson
import okhttp3.*
import java.io.IOException

/**
 * 网络请求工具类
 */
class OkHttpUtils {
    companion object {
        private const val baseUrl = "https://open.douyin.com" // 抖音平台
        private const val mockUrl = "https://mock.apifox.cn/m1/1441581-0-default" // mock 地址
        private const val clientKey = "awr4g04kxg26jk2l" // 需要到开发者网站申请并替换
        private const val clientSecret = "ce6a9c54648b7f99565a66f20fd70866"
        private const val TAG = "okHttp"

        // 验权用的 openid 和 access_token
        private lateinit var openid: String
        private lateinit var token: String

        // 验权模块的 Request 和 数据
        private lateinit var accessRequest: Request
        private lateinit var accessToken: AccessTokenJson

        // 调用凭证模块的Request 和 Client
        // 接口调用的凭证client_access_token，主要用于调用不需要用户授权就可以调用的接口
        private lateinit var ClientAccessRequest: Request
        private val ClientAccessClient = OkHttpClient()
        var clientAccess: String = ""

        // Rank 模块的 Request 和 Client
        private lateinit var movieRequest: Request
        private lateinit var teleplayRequest: Request
        private lateinit var artRequest: Request
        private lateinit var rankVersionRequest: Request
        private val rankClient = OkHttpClient()

        // video 模块 Request 和 Client
        private lateinit var videoRequest: Request
        private val videoClient = OkHttpClient()

        // Friend 模块的 Request 和 Client
        private lateinit var followRequest: Request
        private lateinit var fansRequest: Request
        private lateinit var checkRequest: Request
        private val friendClient = OkHttpClient()

        // 切换主线程执行
        private val handle = Handler(Looper.getMainLooper())

        // Info 模块的 Request 和 Client
        private lateinit var infoRequest: Request
        private val infoClient = OkHttpClient()

        // JSON 处理类
        private val gson = Gson()

        // 上下文
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        // 设定上下文
        fun setContext(context: Context) {
            this.context = context
        }

        /**
         * 从 SharedPreferences 读取 code
         * code 的获取在抖音的 api DouYinEntryActivity 类（官方提供的类）中
         */
        private fun getCode(): String {
            if (this::context.isInitialized) {
                val shp: SharedPreferences = context.getSharedPreferences(
                    "Code",
                    Activity.MODE_PRIVATE
                )
                return shp.getString("authCode", "")!!
            }
            return ""
        }

        /**
         * 通过授权码 code 获取 access_token 的请求
         */
        private fun getAccessRequest(): Request {
            val interfaceUrl = "/oauth/access_token/"
            val url =
                "$baseUrl$interfaceUrl?grant_type=authorization_code&redirect_uri=https://www.bytedance.com/&client_secret=$clientSecret&client_key=$clientKey&code=${getCode()}"
            if (!this::accessRequest.isInitialized) {
                accessRequest = Request.Builder()
                    .url(url)
                    .build()
            }
            Log.d(TAG, "getAccessRequest: $url")
            return accessRequest
        }

        /**
         * 获取 access_token 的 JSON 数据
         */
        @JvmName("getAccessToken1")
        fun getAccessToken(): AccessTokenJson {
            if (!this::accessToken.isInitialized) {
                accessToken = AccessTokenJson() // 默认值

                val shp: SharedPreferences = context.getSharedPreferences(
                    "Code",
                    Activity.MODE_PRIVATE
                )
                val accessTokenJson: String = shp.getString("accessCode", "")!!

                if (accessTokenJson != "")
                    accessToken = gson.fromJson(accessTokenJson, AccessTokenJson::class.java)
            }
            return accessToken
        }

        /**
         * 调用 Client 发送请求，获取 access_token
         */
        fun doAccessGet(callback: OkHttpCallback) {
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


        /**
         * 获取接口调用的凭证 client_access_token 请求
         * client_access_token 主要用于调用不需要用户授权就可以调用的接口
         */
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

        /**
         * 调用 Client 发送请求，获取 client_access_token
         */
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

        /**
         * 获取 client_access_token 实际调用方法，传入了回调对象
         */
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


        /**
         * 获取 电影榜单 请求
         */
        private fun getMovieRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=1"
            val url = mockUrl + interfaceUrl
            if (!this::movieRequest.isInitialized) {
                movieRequest = Request.Builder()
                    .url(url)
                    .addHeader("access-token", clientAccess)
                    .build()
            }
            Log.d(TAG, "getMovieRankRequest: $url")
            return movieRequest
        }

        /**
         * 获取 电视剧榜单 请求
         */
        private fun getTeleplayRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=2"
            val url = mockUrl + interfaceUrl

            teleplayRequest = Request.Builder()
                .url(url)
                .addHeader("access-token", clientAccess)
                .build()

            Log.d("okHttp", "getClientAccess: $clientAccess")
            Log.d(TAG, "getTeleplayRankRequest: $url")
            return teleplayRequest
        }

        /**
         * 获取 综艺榜单 请求
         */
        private fun getArtRankRequest(): Request {
            val interfaceUrl = "/discovery/ent/rank/item/?type=3"
            val url = mockUrl + interfaceUrl

            if (!this::artRequest.isInitialized) {
                artRequest = Request.Builder()
                    .url(url)
                    .addHeader("access-token", clientAccess)
                    .build()
            }
            Log.d(TAG, "getArtRankRequest: $url")
            return artRequest
        }

        /**
         * 调用获取 电影榜单 请求，获取电影榜单
         */
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

        /**
         * 调用获取 电视剧榜单 请求，获取电视剧榜单
         */
        fun doTeleplayGet(callback: OkHttpCallback) {
            rankClient.newCall(getTeleplayRankRequest()).enqueue(object : Callback {
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

        /**
         * 调用获取 综艺榜单 请求，获取综艺榜单
         */
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

        /**
         * 获取榜单版本请求
         */
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

        /**
         * 调用获取榜单版本请求，获取榜单版本
         */
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

        /**
         * 获取榜单版本实际调用方法，传入回调对象
         */
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



        /**
         * 获取 video 请求
         * @param cursor 数据的起始游标
         * @return Request
         * * 注：由于 cursor 可能发生变化，故每次都要重新建立请求
         */
        fun getVideoRequest(cursor: Int = 0): Request {
            val interfaceUrl = "/video/list/"
            val id = getAccessToken().data.open_id
            val token = getAccessToken().data.access_token
            val count = 20  // 每页的数量
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

        /**
         * 调用获取 video 请求，获取视频信息
         */
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



        /**
         * 获取关注列表请求
         */
        private fun getFollowRequest(): Request {
            val interfaceUrl = "/following/list/"
            openid = getAccessToken().data.open_id
            token = getAccessToken().data.access_token
            val count = 20
            val cursor = 0
            val url = "$baseUrl$interfaceUrl?count=$count&open_id=$openid&cursor=$cursor"
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

        /**
         * 获取粉丝列表请求
         */
        private fun getFansRequest(): Request {
            val interfaceUrl = "/fans/list/"
            openid = getAccessToken().data.open_id
            token = getAccessToken().data.access_token
            val count = 20
            val cursor = 0
            val url = "$baseUrl$interfaceUrl?count=$count&open_id=$openid&cursor=$cursor"
            if (!this::fansRequest.isInitialized) {
                fansRequest = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access-token", token)
                    .build()
            }
            Log.d(TAG, "getFansRequest: $url")
            return fansRequest
        }

        /**
         * 粉丝判断请求
         */
        @Synchronized
        private fun getFansCheckRequest(checkId: String): Request {
            val interfaceUrl = "/fans/check/"
            openid = getAccessToken().data.open_id
            token = getAccessToken().data.access_token
            val url = "$baseUrl$interfaceUrl?follower_open_id=$checkId&open_id=$openid"
            checkRequest = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build()
            Log.d(TAG, "getFansCheckRequest: $url")
            return checkRequest
        }

        /**
         * 获取关注列表
         */
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
                    if ((json != null) || (json != "")) {
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

        /**
         * 获取粉丝列表
         */
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

        /**
         * 判断是否为粉丝
         */
        fun doFansCheckGet(checkId: String): String {
            val response = friendClient.newCall(getFansCheckRequest(checkId)).execute()
            Log.i(TAG, "doFansCheckGet: haoye")
            return response.body?.string() ?: ""
        }



        /**
         * 获取用户公开信息的请求
         */
        private fun getInfoRequest(): Request {
            val interfaceUrl = "/oauth/userinfo/"
            val url = baseUrl + interfaceUrl
            // 因为 token 和 openid 可能获取失败，所以无法采用单例模式，每次都要重新获取新的 request
//            if (!this::infoRequest.isInitialized) {
            // 初始化耗时参数
            val token = getAccessToken().data.access_token
            val id = getAccessToken().data.open_id
            val body = FormBody
                .Builder()
                .addEncoded("access_token", token)
                .addEncoded("open_id", id)
                .build()
            Log.d(TAG, "token:$token openid:$id")


            // 初始化 infoRequest
            infoRequest = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .post(body)
                .build()
//            }
            return infoRequest
        }

        /**
         * 调用 Client 发送请求，获取用户公开信息
         */
        fun doInfoPost(callback: OkHttpCallback) {
            infoClient.newCall(getInfoRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "doInfoPost: $e")
                    handle.post {
                        callback.isFail()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "doInfoPost: ${response.body}")
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