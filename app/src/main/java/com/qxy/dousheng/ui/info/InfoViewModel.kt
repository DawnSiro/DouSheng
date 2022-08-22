package com.qxy.dousheng.ui.info

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.InfoDao
import com.qxy.dousheng.database.DouShengDatabase
import com.qxy.dousheng.model.info.InfoItem
import com.qxy.dousheng.model.info.InfoJson
import com.qxy.dousheng.network.InfoOkHttpUtils
import com.qxy.dousheng.network.OkHttpCallback

class InfoViewModel(application: Application) : AndroidViewModel(application) {
    private var infoDao: InfoDao
    private val allInfoItemLiveData: LiveData<List<InfoItem>>

    init {
        val database = DouShengDatabase.getDatabase(application)
        infoDao = database.getInfoItemDao()
        allInfoItemLiveData = infoDao.getAllInfoLiveData()
    }

    fun getLiveData(): LiveData<List<InfoItem>> = allInfoItemLiveData

    private fun clear() {
        ClearItem(infoDao).execute()
    }

    fun insert(vararg infoItem: InfoItem) {
        InsertItem(infoDao).execute(*infoItem)
    }

    fun doInfoPost() {
        InfoOkHttpUtils.doInfoPost(object : OkHttpCallback {
            override fun isFail() {
                Log.d("okHttp", "isFail: doInfoPost")
            }

            override fun isSuccess(json: String?) {
                if (json == null) {
                    Log.d("okHttp", "isSuccess: json is null")
                } else {
                    Log.d("okHttp", "isSuccess: json is ok")
                    update(json)
                }
            }

        })
    }

    fun update(response: String) {
        clear()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")
        val infoJson: InfoJson = gson.fromJson(response, InfoJson::class.java)
        Log.d("okHttp", "update: $infoJson")

        insert(infoJson.data)
    }


    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val infoDao: InfoDao) : AsyncTask<InfoItem, Int, Boolean>() {
        override fun doInBackground(vararg params: InfoItem): Boolean {
            infoDao.insertItem(*params)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val infoDao: InfoDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            infoDao.clearItem()
            return true
        }
    }

}