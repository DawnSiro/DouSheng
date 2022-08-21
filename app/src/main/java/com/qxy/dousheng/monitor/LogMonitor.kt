package com.qxy.dousheng.monitor

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log

public object LogMonitor {
    private val TAG = "LogMonitor"
    private var mIoHandler: Handler
    //方法耗时的卡口,300毫秒
    private val TIME_BLOCK = 300L

    private val mLogRunnable = Runnable() {
        //打印出执行的耗时方法的栈消息
        val sb = StringBuilder()
        val stackTrace = Looper.getMainLooper().thread.stackTrace
        for (s in stackTrace) {
            sb.append(s.toString())
            sb.append("\n")
        }
        Log.e(TAG,sb.toString())
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
    init {
        val logThread = HandlerThread("log")
        logThread.start()
        mIoHandler = Handler(logThread.looper)
    }
}