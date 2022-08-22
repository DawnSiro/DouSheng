package com.qxy.dousheng.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.qxy.dousheng.model.rank.RankItem

/**
 * DAO(Data Access Object) 数据访问对象
 * 由 room 提供具体实现
 */
@Dao
interface RankDao {
    @Insert
    fun insertItem(vararg rankItem: RankItem)

    @Update
    fun updateItem(vararg rankItem: RankItem)

    @Delete
    fun deleteItem(vararg rankItem: RankItem)

    @Query("DELETE FROM RankItem")
    fun clearItem()

    @Query("DELETE FROM RankItem WHERE type = 1")
    fun clearMovieItem()

    @Query("DELETE FROM RankItem WHERE type = 2")
    fun clearVideoItem()

    @Query("DELETE FROM RankItem WHERE type = 3")
    fun clearArtItem()

    @Query("SELECT * FROM RankItem WHERE type = 1 ORDER BY hot DESC")
    fun allMovieItemLive(): LiveData<List<RankItem>>

    @Query("SELECT * FROM RankItem WHERE type = 2 ORDER BY hot DESC")
    fun allVideoItemLive(): LiveData<List<RankItem>>

    @Query("SELECT * FROM RankItem WHERE type = 3 ORDER BY hot DESC")
    fun allArtItemLive(): LiveData<List<RankItem>>
}