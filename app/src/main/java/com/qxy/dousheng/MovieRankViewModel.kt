package com.qxy.dousheng

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MovieRankViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var itemDao: ItemDao
    private lateinit var allItemLive: LiveData<List<Item>>

    init {
        val itemDatabase = ItemDatabase.getDatabase(application.applicationContext)
        itemDao = itemDatabase.getItemDao()
        allItemLive = itemDao.allItemLive()
    }

    fun insertItem(vararg item: Item) {
        InsertItem(itemDao).execute(*item)
    }

    fun clearItem() {
        ClearItem(itemDao).execute()
    }

    fun getLiveData(): LiveData<List<Item>> {
        return allItemLive
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val itemDao: ItemDao) : AsyncTask<Item, Int, Boolean>() {
        override fun doInBackground(vararg item: Item): Boolean {
            itemDao.insertItem(*item)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val itemDao: ItemDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg void: Void): Boolean {
            itemDao.clearItem()
            return true
        }
    }
}