package com.qxy.dousheng

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.VideoDao
import com.qxy.dousheng.database.VideoDatabase
import com.qxy.dousheng.model.VideoItem
import com.qxy.dousheng.model.VideoJson
import com.qxy.dousheng.model.VideoList

class VideoViewModel(application: Application) : AndroidViewModel(application) {
    private var videoDao: VideoDao
    private val allVideoLiveData: LiveData<List<VideoItem>>
    var updateFlag = true

    init {
        val videoDatabase = VideoDatabase.getDatabase(application)
        videoDao = videoDatabase.getItemDao()
        allVideoLiveData = videoDao.allVideoLiveData()
    }

    fun getLiveData(): LiveData<List<VideoItem>> = allVideoLiveData

    fun clear() {
        ClearItem(videoDao).execute()
    }

    fun insert(vararg videoItem: VideoItem) {
        InsertItem(videoDao).execute(*videoItem)
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
        override fun doInBackground(vararg params: VideoItem): Boolean {
            videoDao.insertItem(*params)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val videoDao: VideoDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            videoDao.clearItem()
            return true
        }
    }
}