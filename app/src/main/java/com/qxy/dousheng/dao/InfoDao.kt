package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qxy.dousheng.model.info.InfoItem

/**
 * DAO(Data Access Object) 数据访问对象
 * 由 room 提供具体实现
 */
@Dao
interface InfoDao {
    @Insert
    fun insertItem(vararg infoItem: InfoItem)

    @Query("DELETE FROM InfoItem")
    fun clearItem()

    @Query("SELECT * FROM InfoItem")
    fun getAllInfoLiveData(): LiveData<List<InfoItem>>
}