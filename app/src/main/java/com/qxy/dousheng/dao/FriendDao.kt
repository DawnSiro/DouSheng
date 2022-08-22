package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qxy.dousheng.model.friend.FriendItem

/**
 * DAO(Data Access Object) 数据访问对象
 * 由 room 提供具体实现
 */
@Dao
interface FriendDao {
    @Insert
    fun insertItem(vararg friendItem: FriendItem)

    @Query("DELETE FROM FriendItem WHERE flag = 1")
    fun clearFansItem()

    @Query("DELETE FROM FriendItem WHERE flag = 2")
    fun clearFollowItem()

    @Query("DELETE FROM FriendItem")
    fun clearItem()

    @Query("SELECT * FROM FriendItem WHERE flag = 1")
    fun getAllFansLiveData(): LiveData<List<FriendItem>>

    @Query("SELECT * FROM FriendItem WHERE flag = 2")
    fun getAllFollowLiveData(): LiveData<List<FriendItem>>

    @Query("SELECT * FROM FriendItem")
    fun getAllFriendLiveData(): LiveData<List<FriendItem>>
}