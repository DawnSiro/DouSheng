package com.qxy.dousheng.ui.video

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.VideoView

class MyVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : VideoView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("MyVideoView", "MyVideoView1 size = $measuredWidth:$measuredHeight")
        val measuredWidth = getDefaultSize(0,widthMeasureSpec)
        val measuredHeight = getDefaultSize(0,heightMeasureSpec)
        setMeasuredDimension(measuredWidth,measuredHeight)
        Log.d("MyVideoView", "MyVideoView size = $measuredWidth:$measuredHeight")
    }
}