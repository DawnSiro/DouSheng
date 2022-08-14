package com.qxy.dousheng.network

interface OkHttpCallback {
    fun isFail()
    fun isSuccess(json: String?)
}