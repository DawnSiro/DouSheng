package com.qxy.dousheng.ui.video

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.PlayDao
import com.qxy.dousheng.database.PlayDatabase
import com.qxy.dousheng.model.video.PlayItem

class PlayViewModel(application: Application) : AndroidViewModel(application) {
    private var playDao: PlayDao
    private val allPlayLiveData: LiveData<List<PlayItem>>

    init {
        val playDatabase = PlayDatabase.getDatabase(application)
        playDao = playDatabase.getItemDao()
        allPlayLiveData = playDao.allPlayLiveData()
    }

    fun getLiveData(): LiveData<List<PlayItem>> = allPlayLiveData

    fun clear() {
        ClearItem(playDao).execute()
    }

    fun insert(vararg playItem: PlayItem) {
        InsertItem(playDao).execute(*playItem)
    }

    fun update(response: String) {

        Log.d("TAG", "update: $response")

        clear()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")
        val playItem = gson.fromJson(response, PlayItem::class.java)
        Log.d("okHttp", "update: $playItem")

        insert(playItem)

    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val playDao: PlayDao) : AsyncTask<PlayItem, Int, Boolean>() {
        override fun doInBackground(vararg params: PlayItem): Boolean {
            playDao.insertItem(*params)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val playDao: PlayDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            playDao.clearItem()
            return true
        }
    }
}