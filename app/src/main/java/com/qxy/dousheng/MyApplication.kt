package com.qxy.dousheng

import android.app.Application

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        BlockDetectByPrinter.start()
    }
}