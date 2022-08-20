package com.qxy.dousheng

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.Choreographer

class DouShengApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //性能耗时监控
        BlockDetectByPrinter.start()

        Choreographer.getInstance().postFrameCallback(FPSFrameCallback(System.nanoTime()))

    }
    inner class FPSFrameCallback(lastFrameTimeNanos: Long) : Choreographer.FrameCallback {
        private var mLastFrameTimeNanos: Long = 0
        private val mFrameIntervalNanos: Long

        override fun doFrame(frameTimeNanos: Long) {
            // 初始化时间
            if (mLastFrameTimeNanos == 0L) {
                mLastFrameTimeNanos = frameTimeNanos
            }
            val jitterNanos = frameTimeNanos - mLastFrameTimeNanos
            if (jitterNanos >= mFrameIntervalNanos) {
                val skippedFrames = jitterNanos / mFrameIntervalNanos
                if (skippedFrames > 30) {
                    // 丢帧30以上打印日志
                    Log.i(
                        "FPS_TEST", "Skipped " + skippedFrames + " frames!  "
                                + "The application may be doing too much work on its main thread."
                    )
                }
            }
            mLastFrameTimeNanos = frameTimeNanos
            // 注册下一帧回调
            Choreographer.getInstance().postFrameCallback(this)
        }

        init {
            mLastFrameTimeNanos = lastFrameTimeNanos
            mFrameIntervalNanos = (1000000000 / 60.0).toLong()
        }
    }
}