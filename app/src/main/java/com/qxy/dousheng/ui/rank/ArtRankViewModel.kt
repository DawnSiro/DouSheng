package com.qxy.dousheng.ui.rank

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.RankDao
import com.qxy.dousheng.database.DouShengDatabase
import com.qxy.dousheng.model.rank.RankItem
import com.qxy.dousheng.model.rank.RankJson
import com.qxy.dousheng.network.OkHttpCallback
import com.qxy.dousheng.network.RankOkHttpUtils

class ArtRankViewModel(application: Application) : AndroidViewModel(application) {
    private var rankDao: RankDao
    private var allRankItemLive: LiveData<List<RankItem>>

    init {
        val database = DouShengDatabase.getDatabase(application.applicationContext)
        rankDao = database.getRankItemDao()
        allRankItemLive = rankDao.allArtItemLive()
    }

    private fun insertItem(vararg rankItem: RankItem) {
        InsertItem(rankDao).execute(*rankItem)
    }

    private fun clearItem() {
        ClearItem(rankDao).execute()
    }


    fun getLiveData(): LiveData<List<RankItem>> {
        return allRankItemLive
    }

    fun doGet() {
        RankOkHttpUtils.doArtGet(object : OkHttpCallback {
            override fun isFail() {
                Log.d("okHttp", "doArtGet 出错")
            }

            override fun isSuccess(json: String?) {
                if (json != null) {
                    Log.d("okHttp", "doArtGet: $json")
                    update(json)
                } else {
                    Log.d("okHttp", "doArtGet: json=null")
                }
            }
        })
    }

    fun update(response: String) {
        clearItem()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")

        Log.d("okHttp", "update: $response")

        val rankJson = gson.fromJson(response, RankJson::class.java)
        for (i in rankJson.data.list) {
            Log.d("okHttp", "update: ${i.name}")
            insertItem(RankItem(i.name, i.poster, i.release_date, i.hot, 3))
        }
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val rankDao: RankDao) : AsyncTask<RankItem, Int, Boolean>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg rankItem: RankItem): Boolean {
            rankDao.insertItem(*rankItem)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val rankDao: RankDao) : AsyncTask<Void, Int, Boolean>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg void: Void): Boolean {
            rankDao.clearArtItem()
            return true
        }
    }
}