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
import com.qxy.dousheng.network.OkHttpUtils

/**
 * 电影榜单 ViewModel 类，对相关数据进行监控并及时同步到 View
 */
class MovieRankViewModel(application: Application) : AndroidViewModel(application) {
    private var rankDao: RankDao
    private var allRankItemLive: LiveData<List<RankItem>>

    init {
        val database = DouShengDatabase.getDatabase(application.applicationContext)
        rankDao = database.getRankItemDao()
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

    fun doGet() {
        // TODO 接上版本
        OkHttpUtils.doMovieGet(143, object : OkHttpCallback {
            override fun isFail() {
                Log.d("okHttp", "doMovieGet 出错")
            }

            override fun isSuccess(json: String?) {
                if (json != null && json != "{}") {
                    Log.d("okHttp", "doMovieGet: $json")
                    update(json)
                } else {
                    Log.d("okHttp", "doMovieGet: json=null")
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

            // 将导演转储成字符串
            val directors = StringBuilder()
            for(index in i.directors.indices) {
                directors.append(i.directors[index])
                if(index != i.directors.size - 1){
                   directors.append("/") // 如果不是最后一个，就加上 / 分隔
                }
            }
            Log.d("Movie", "update: 字符串转储完成")

            // 将上映地区转储成字符串
            val areas = StringBuilder()
            for(index in i.areas.indices) {
                areas.append(i.areas[index])
                if(index != i.areas.size - 1){
                    areas.append("/") // 如果不是最后一个，就加上 / 分隔
                }
            }

            // 将演员转储成字符串
            val actors = StringBuilder()
            for(index in i.actors.indices) {
                actors.append(i.actors[index])
                if(index != i.actors.size - 1){
                    actors.append("/") // 如果不是最后一个，就加上 / 分隔
                }
            }

            // 将演员转储成字符串
            val tags = StringBuilder()
            for(index in i.tags.indices) {
                tags.append(i.tags[index])
                if (index != i.tags.size - 1) {
                    tags.append("/") // 如果不是最后一个，就加上 / 分隔
                }
            }

            insertItem(RankItem(directors.toString(),
                areas.toString(),
                actors.toString(),
                i.hot,
                i.discussion_hot,
                i.topic_hot,
                i.search_hot,
                i.influence_hot,
                i.name,
                i.name_en,
                i.poster,
                i.release_date,
                tags.toString(), 1)) // 类型：1=电影 2=电视剧 3=综艺
            Log.d("Movie", "update: 数据库")
        }
        Log.d("Movie", "update: 总体字符串转储完成")
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
            rankDao.clearMovieItem()
            return true
        }
    }
}