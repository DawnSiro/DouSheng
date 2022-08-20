package com.qxy.dousheng.ui.friend

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.FriendDao
import com.qxy.dousheng.database.FriendDatabase
import com.qxy.dousheng.model.FriendItem
import com.qxy.dousheng.model.FriendJson

class FansViewModel(application: Application) : AndroidViewModel(application) {
    private var friendDao: FriendDao
    private val allFansLiveData: LiveData<List<FriendItem>>

    init {
        val friendDatabase = FriendDatabase.getDatabase(application)
        friendDao = friendDatabase.getItemDao()
        allFansLiveData = friendDao.getAllFansLiveData()
    }

    fun getLiveData(): LiveData<List<FriendItem>> = allFansLiveData

    private fun clear() {
        ClearItem(friendDao).execute()
    }

    fun insert(vararg friendItem: FriendItem) {
        InsertItem(friendDao).execute(*friendItem)
    }

    fun update(response: String) {
        clear()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")
        val friendJson: FriendJson = gson.fromJson(response, FriendJson::class.java)
        Log.d("okHttp", "update: $friendJson")

        for (i in friendJson.data.list) {
            insert(
                FriendItem(
                    i.avatar,
                    i.nickname,
                    i.gender,
                    i.country,
                    i.province,
                    i.city,
                    1,
                    1
                )
            )
        }
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val friendDao: FriendDao) :
        AsyncTask<FriendItem, Int, Boolean>() {
        override fun doInBackground(vararg params: FriendItem): Boolean {
            friendDao.insertItem(*params)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val friendDao: FriendDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            friendDao.clearFansItem()
            return true
        }
    }

}