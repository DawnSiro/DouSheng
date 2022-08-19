package com.qxy.dousheng.util

import android.R
import android.hardware.camera2.params.ColorSpaceTransform
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


// Glide 工具包，处理用图片用
class GlideUtils {

    companion object{
        private const val TAG = "GlideUtil"

        // 将图片变成圆形
        fun loadCircle(view: View, url: String, imageView: ImageView) {
            // 设置
            val options: RequestOptions  = RequestOptions().transform(CircleCrop())

            Log.i(TAG, "loadCircle: $url")

            // 加载图片
            Glide.with(view)
                .load(url)
                .apply(options)
                .into(imageView)

        }

    }

}