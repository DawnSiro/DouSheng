package com.qxy.dousheng.ui.friend

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.FriendDao
import com.qxy.dousheng.database.DouShengDatabase
import com.qxy.dousheng.model.friend.FansCheckJson
import com.qxy.dousheng.model.friend.FriendItem
import com.qxy.dousheng.model.friend.FriendJson
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.OkHttpUtils

/**
 * 关注列表 ViewModel 类，对相关数据进行监控并及时同步到 View
 */
class FollowViewModel(application: Application) : AndroidViewModel(application) {
    private var friendDao: FriendDao
    private val allFollowLiveData: LiveData<List<FriendItem>>

    init {
        val douShengDatabase = DouShengDatabase.getDatabase(application)
        friendDao = douShengDatabase.getFriendItemDao()
        allFollowLiveData = friendDao.getAllFollowLiveData()
    }

    fun getLiveData(): LiveData<List<FriendItem>> = allFollowLiveData

    private fun clear() {
        ClearItem(friendDao).execute()
    }

    fun insert(vararg friendItem: FriendItem) {
        InsertItem(friendDao).execute(*friendItem)
    }

    fun doGet() {
        OkHttpUtils.doFollowGet(object : OkHttpCallback {
            override fun isFail() {
                Log.d("okHttp", "doFollowGet 出错")

            }

            override fun isSuccess(json: String?) {
                if (json != null && json != "") {
                    Log.d("okHttp", "doFollowGet: $json")
                    update(json)
                } else {
                    Log.d("okHttp", "doFollowGet: json=null")
                }
            }
        })
    }

    fun update(response: String) {
        if (response != "") {
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
                        i.open_id,
                        1,
                        2
                    )
                )
            }
        }
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val friendDao: FriendDao) :
        AsyncTask<FriendItem, Int, Boolean>() {
        override fun doInBackground(vararg params: FriendItem): Boolean {
            for (i in params) {
                val gson = Gson()
                val json = OkHttpUtils.doFansCheckGet(i.open_id)
                Log.d("okHttp", "doFansCheckGet: ${i.name} $json")
                val checkJson = gson.fromJson(json, FansCheckJson::class.java)
                i.isFollow = if (checkJson.data.is_follower) 3 else 1
                Log.d("okHttp", "doFansCheckGet: ${i.name} ${i.isFollow}")

                friendDao.insertItem(i)
            }
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val friendDao: FriendDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            friendDao.clearFollowItem()
            return true
        }
    }
}