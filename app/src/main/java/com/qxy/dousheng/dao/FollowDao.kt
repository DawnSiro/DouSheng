package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.qxy.dousheng.model.FansItem
import com.qxy.dousheng.model.FollowItem
import com.qxy.dousheng.model.RankItem

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