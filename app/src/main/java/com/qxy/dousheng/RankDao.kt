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

    @Query("SELECT * FROM RankItem ORDER BY hot DESC")
    fun allItemLive(): LiveData<List<RankItem>>

}