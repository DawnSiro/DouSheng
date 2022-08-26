package com.qxy.dousheng

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.Choreographer
import com.qxy.dousheng.monitor.BlockDetectByPrinter

class DouShengApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //Performance time-consuming monitoring
        BlockDetectByPrinter.start()
        //Sliding FPS monitoring
        Choreographer.getInstance().postFrameCallback(FPSFrameCallback(System.nanoTime()))
    }

    /**
     * FPS 回调
     */
    inner class FPSFrameCallback(lastFrameTimeNanos: Long) : Choreographer.FrameCallback {
        private var mLastFrameTimeNanos: Long = 0
        private val mFrameIntervalNanos: Long

        override fun doFrame(frameTimeNanos: Long) {
            // initialization time
            if (mLastFrameTimeNanos == 0L) {
                mLastFrameTimeNanos = frameTimeNanos
            }
            val jitterNanos = frameTimeNanos - mLastFrameTimeNanos
            if (jitterNanos >= mFrameIntervalNanos) {
                val skippedFrames = jitterNanos / mFrameIntervalNanos
                    // print drop frame log
                    Log.i("FPS_TEST", "Skipped " + skippedFrames + " frames!  ")
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