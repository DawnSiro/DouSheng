package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.qxy.dousheng.model.VideoItem

@Dao
interface VideoDao {
    @Insert
    fun insertItem(vararg videoItem: VideoItem)

    @Delete
    fun deleteItem(vararg videoItem: VideoItem)

    @Query("DELETE FROM VideoItem")
    fun clearItem()

    @Query("SELECT * FROM VideoItem")
    fun allVideoLiveData(): LiveData<List<VideoItem>>
}