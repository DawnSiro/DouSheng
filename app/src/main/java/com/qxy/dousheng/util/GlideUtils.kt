package com.qxy.dousheng.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


/**
 * Glide 工具包，处理用图片用
 */
class GlideUtils {
    companion object{
        private const val TAG = "GlideUtil"

        /**
         * 将图片处理成圆形(View)
         */
        fun loadCircle(view: View, url: String, imageView: ImageView) {
            // 设置图片
            val options: RequestOptions = RequestOptions().transform(CircleCrop())
            // 加载图片
            Glide.with(view)
                .load(url)
                .apply(options)
                .into(imageView)
            Log.i(TAG, "loadCircle: $url")
        }

        /**
         * 将图片处理成圆形(Fragment)
         */
        fun loadCircle(fragment: Fragment, url: String, imageView: ImageView) {
            // 设置图片
            val options: RequestOptions = RequestOptions().transform(CircleCrop())
            // 加载图片
            Glide.with(fragment)
                .load(url)
                .apply(options)
                .into(imageView)
            Log.i(TAG, "loadCircle: $url")
        }

        /**
         * 将图片处理成圆形，并指定大小
         */
        fun loadCircle(fragment: Fragment, url: String, imageView: ImageView, size: Int) {
            // 设置
            val options: RequestOptions  = RequestOptions().transform(CircleCrop())

            Log.i(TAG, "loadCircle: $url")

            // 加载图片
            Glide.with(fragment)
                .load(url)
                .override(900)
                .apply(options)
                .into(imageView)

        }

    }

}