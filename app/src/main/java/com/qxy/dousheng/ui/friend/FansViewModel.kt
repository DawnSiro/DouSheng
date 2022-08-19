package com.qxy.dousheng.ui.friend

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.FollowDao
import com.qxy.dousheng.database.FollowDatabase
import com.qxy.dousheng.model.FollowBean
import com.qxy.dousheng.model.FollowItem

class FansViewModel(application: Application) : AndroidViewModel(application) {

    private var followDao: FollowDao
    private var followLiveData: LiveData<List<FollowItem>>

    init {
        val followDatabase = FollowDatabase.getDatabase(application.applicationContext)
        followDao = followDatabase.getFollowDao()
        followLiveData = followDao.getFollowItemLive()
    }

    private fun insertItem(vararg followItem: FollowItem) {
        InsertItem(followDao).execute(*followItem)
    }

    private fun clearItem() {
        ClearItem(followDao).execute()
    }


    fun getLiveData(): LiveData<List<FollowItem>> {
        return followLiveData
    }

    fun update(response: String) {
        clearItem()
        Log.d("okHttp", "clearFollowItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")

        Log.d("okHttp", "update: $response")

        // 解析 json
        val bean = gson.fromJson(response, FollowBean::class.java)
        // 选择用到的数据存入数据库
        for (i in bean.data.list) {
            Log.d("okHttp", "update: ${i.nickname}")

            insertItem(
                FollowItem(i.avatar, i.nickname, i.gender, i.country,
                i.province, i.city, isFollow = false)
            ) // TODO 找到是否关注了粉丝的方法，目前默认未关注
        }
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val followDao: FollowDao) : AsyncTask<FollowItem, Int, Boolean>() {
        override fun doInBackground(vararg followItem: FollowItem): Boolean {
            followDao.insertFollowItem(*followItem)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val followDao: FollowDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg void: Void): Boolean {
            followDao.clearFollowItem()
            return true
        }
    }
}