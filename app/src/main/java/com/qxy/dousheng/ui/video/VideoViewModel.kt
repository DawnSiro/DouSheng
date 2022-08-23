package com.qxy.dousheng.ui.video

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.VideoDao
import com.qxy.dousheng.database.DouShengDatabase
import com.qxy.dousheng.model.video.VideoItem
import com.qxy.dousheng.model.video.VideoJson
import com.qxy.dousheng.model.video.VideoList
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.OkHttpUtils

/**
 * 视频数据 ViewModel 类，对相关数据进行监控并及时同步到 View
 */
class VideoViewModel(application: Application) : AndroidViewModel(application) {
    private var videoDao: VideoDao
    private val allVideoLiveData: LiveData<List<VideoItem>>
    var updateFlag = true

    init {
        val database = DouShengDatabase.getDatabase(application)
        videoDao = database.getVideoItemDao()
        allVideoLiveData = videoDao.allVideoLiveData()
    }

    fun getLiveData(): LiveData<List<VideoItem>> = allVideoLiveData

    private fun clear() {
        ClearItem(videoDao).execute()
    }

    fun insert(vararg videoItem: VideoItem) {
        InsertItem(videoDao).execute(*videoItem)
    }

    fun doGet() {
        OkHttpUtils.doVideoGet(object : OkHttpCallback {
            override fun isFail() {
                Log.d("okHttp", "doTeleplayGet 出错")
            }

            override fun isSuccess(json: String?) {
                if (json != null && json != "") {
                    Log.d("okHttp", "doTeleplayGet: $json")
                    update(json)
                } else {
                    Log.d("okHttp", "doTeleplayGet: json=null")
                }
            }
        })
    }

    fun update(response: String) {
        clear()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")
        val videoJson: VideoJson = gson.fromJson(response, VideoJson::class.java)
        Log.d("okHttp", "update: $videoJson")

        // 提取视频列表
        val videoList: Array<VideoList> = videoJson.data.list
        for (video in videoList) {
            val videoItem = VideoItem(
                video.title,
                video.cover,
                video.create_time,
                video.is_top,
                video.is_reviewed,
                video.video_status,
                video.video_id,
                video.share_url,
                video.item_id,
                video.media_type,
                video.statistics.forward_count,
                video.statistics.digg_count,
                video.statistics.download_count,
                video.statistics.play_count,
                video.statistics.share_count,
                video.statistics.comment_count
            )
            insert(videoItem)
        }

        updateFlag = videoJson.data.has_more
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val videoDao: VideoDao) : AsyncTask<VideoItem, Int, Boolean>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: VideoItem): Boolean {
            videoDao.insertItem(*params)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val videoDao: VideoDao) : AsyncTask<Void, Int, Boolean>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Boolean {
            videoDao.clearItem()
            return true
        }
    }
}