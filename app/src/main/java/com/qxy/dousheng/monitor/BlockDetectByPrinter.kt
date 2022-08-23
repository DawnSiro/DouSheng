package com.qxy.dousheng.monitor

import android.os.Looper
import android.util.Printer

/**
 * 性能耗时监控
 */
class BlockDetectByPrinter {
    companion object {
        fun start() {
            Looper.getMainLooper().setMessageLogging(object : Printer {
                //分发和处理消息开始前的log
                private val START = ">>>>> Dispatching"
                //分发和处理消息结束后的log
                private val END = "<<<<< Finished"
                override fun println(x: String) {
                    if (x.startsWith(START)) {
                        //开始计时
                        LogMonitor.startMonitor()
                    }
                    if (x.startsWith(END)) {
                        //结束计时，并计算出方法执行时间
                        LogMonitor.removeMonitor()
                    }
                }
            })
        }
    }
}