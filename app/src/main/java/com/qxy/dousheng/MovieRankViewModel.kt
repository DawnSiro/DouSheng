package com.qxy.dousheng

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MovieRankViewModel(application: Application) : AndroidViewModel(application) {
    private var movieDao: MovieDao
    private var allMovieItemLive: LiveData<List<MovieItem>>

    init {
        val itemDatabase = ItemDatabase.getDatabase(application.applicationContext)
        movieDao = itemDatabase.getItemDao()
        allMovieItemLive = movieDao.allItemLive()
    }

    fun insertItem(vararg movieItem: MovieItem) {
        InsertItem(movieDao).execute(*movieItem)
    }

    fun clearItem() {
        ClearItem(movieDao).execute()
    }


    fun getLiveData(): LiveData<List<MovieItem>> {
        return allMovieItemLive
    }

    // 后台异步插入数据
    @SuppressLint("StaticFieldLeak")
    inner class InsertItem(private val movieDao: MovieDao) : AsyncTask<MovieItem, Int, Boolean>() {
        override fun doInBackground(vararg movieItem: MovieItem): Boolean {
            movieDao.insertItem(*movieItem)
            return true
        }
    }

    // 后台异步清除数据
    @SuppressLint("StaticFieldLeak")
    inner class ClearItem(private val movieDao: MovieDao) : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg void: Void): Boolean {
            movieDao.clearItem()
            return true
        }
    }
}