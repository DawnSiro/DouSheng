package com.qxy.dousheng

interface OkHttpCallback {
    fun isFail()
    fun isSuccess(json: String?)
}