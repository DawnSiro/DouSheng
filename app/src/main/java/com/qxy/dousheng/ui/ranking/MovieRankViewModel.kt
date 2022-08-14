package com.qxy.dousheng.ui.ranking

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.model.Item
import com.qxy.dousheng.database.ItemDatabase
import com.qxy.dousheng.dao.RankDao
import com.qxy.dousheng.model.RankItem

class MovieRankViewModel(application: Application) : AndroidViewModel(application) {
    private var rankDao: RankDao
    private var allRankItemLive: LiveData<List<RankItem>>

    init {
        val itemDatabase = ItemDatabase.getDatabase(application.applicationContext)
        rankDao = itemDatabase.getItemDao()
        allRankItemLive = rankDao.allMovieItemLive()
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

    fun update(response: String) {
        clearItem()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")

        Log.d("okHttp", "update: $response")

        val item = gson.fromJson(response, Item::class.java)
        for (i in item.data.list) {
            Log.d("okHttp", "update: ${i.name}")
            insertItem(RankItem(i.name, i.poster, i.release_date, i.hot, 1))
        }
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val rankDao: RankDao) : AsyncTask<RankItem, Int, Boolean>() {
        override fun doInBackground(vararg rankItem: RankItem): Boolean {
            rankDao.insertItem(*rankItem)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val rankDao: RankDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg void: Void): Boolean {
            rankDao.clearItem()
            return true
        }
    }
}