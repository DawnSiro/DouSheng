package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.qxy.dousheng.model.video.PlayItem

@Dao
interface PlayDao {

    @Insert
    fun insertItem(vararg playItem: PlayItem)

    @Delete
    fun deleteItem(vararg playItem: PlayItem)

    @Query("DELETE FROM PlayItem")
    fun clearItem()

    @Query("SELECT * FROM PlayItem")
    fun allPlayLiveData(): LiveData<List<PlayItem>>

}