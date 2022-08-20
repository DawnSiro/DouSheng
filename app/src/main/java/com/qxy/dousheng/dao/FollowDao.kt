package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.qxy.dousheng.model.friend.FansItem
import com.qxy.dousheng.model.friend.FollowItem

@Dao
interface FollowDao {
    @Insert
    fun insertFollowItem(vararg followItem: FollowItem)

    @Update
    fun updateFollowItem(vararg followItem: FollowItem)

    @Delete
    fun deleteFollowItem(vararg followItem: FollowItem)

    @Query("DELETE FROM FollowItem")
    fun clearFollowItem()

    @Query("SELECT * FROM FollowItem")
    fun getFollowItemLive(): LiveData<List<FollowItem>>

    @Insert
    fun insertFansItem(vararg fansItem: FansItem)

    @Update
    fun updateFansItem(vararg fansItem: FansItem)

    @Delete
    fun deleteFansItem(vararg fansItem: FansItem)

    @Query("DELETE FROM FansItem")
    fun clearFansItem()

    @Query("SELECT * FROM FansItem")
    fun getFansItemLive(): LiveData<List<FansItem>>

}