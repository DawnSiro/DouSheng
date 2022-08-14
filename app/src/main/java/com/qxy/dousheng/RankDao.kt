package com.qxy.dousheng

import androidx.lifecycle.LiveData
import androidx.room.*

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

    @Query("SELECT * FROM RankItem WHERE type = 1 ORDER BY hot DESC")
    fun allMovieItemLive(): LiveData<List<RankItem>>

    @Query("SELECT * FROM RankItem WHERE type = 2 ORDER BY hot DESC")
    fun allVideoItemLive(): LiveData<List<RankItem>>

    @Query("SELECT * FROM RankItem WHERE type = 3 ORDER BY hot DESC")
    fun allArtItemLive(): LiveData<List<RankItem>>
}