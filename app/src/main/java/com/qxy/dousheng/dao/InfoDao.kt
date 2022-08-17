package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qxy.dousheng.model.InfoItem

@Dao
interface InfoDao {
    @Insert
    fun insertItem(vararg infoItem: InfoItem)

    @Query("DELETE FROM InfoItem")
    fun clearItem()

    @Query("SELECT * FROM InfoItem")
    fun getAllInfoLiveData(): LiveData<List<InfoItem>>
}