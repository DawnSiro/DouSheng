package com.qxy.dousheng

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.qxy.dousheng.dao.InfoDao
import com.qxy.dousheng.database.InfoDatabase
import com.qxy.dousheng.model.InfoItem
import com.qxy.dousheng.model.InfoJson

class InfoViewModel(application: Application) : AndroidViewModel(application) {
    private var infoDao: InfoDao
    private val allInfoItemLiveData: LiveData<List<InfoItem>>

    init {
        val infoDatabase = InfoDatabase.getDatabase(application)
        infoDao = infoDatabase.getItemDao()
        allInfoItemLiveData = infoDao.getAllInfoLiveData()

    }

    fun getLiveData(): LiveData<List<InfoItem>> = allInfoItemLiveData

    fun clear() {
        ClearItem(infoDao).execute()
    }

    fun insert(vararg infoItem: InfoItem) {
        InsertItem(infoDao).execute(*infoItem)
    }

    fun update(response: String) {
        clear()
        Log.d("okHttp", "clearItem: 清除成功")
        val gson = Gson()
        Log.d("okHttp", "Gson: 初始化成功")
        Log.d("okHttp", "update: $response")

        val infoJson: InfoJson = gson.fromJson(response, InfoJson::class.java)
        Log.d("okHttp", "update: ${infoJson.toString()}")

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