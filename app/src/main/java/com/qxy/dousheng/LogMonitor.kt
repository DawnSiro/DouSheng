package com.qxy.dousheng

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log

object LogMonitor {
    private val TAG = "LogMonitor"
    private val sInstance:LogMonitor = LogMonitor
    private lateinit var mIoHandler: Handler
    private val TIME_BLOCK = 300L
    fun LogMonitor() {
        val logThread = HandlerThread("log")
        logThread.start()
        mIoHandler = Handler(logThread.looper)
    }

    private val mLogRunnable = Runnable() {
        fun run() {
            val sb = StringBuilder()
            val stackTrace = Looper.getMainLooper().thread.stackTrace
            for (s in stackTrace) {
                sb.append(s.toString())
                sb.append("\n")
            }
            Log.e(TAG,sb.toString())
        }
    }
    /**
     * 开始计时
     */
    fun startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK)
    }

    /**
     * 停止计时
     */
    fun removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable)
    }
}