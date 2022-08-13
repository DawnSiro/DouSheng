package com.qxy.dousheng

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    fun insertItem(vararg movieItem: MovieItem)

    @Update
    fun updateItem(vararg movieItem: MovieItem)

    @Delete
    fun deleteItem(vararg movieItem: MovieItem)

    @Query("DELETE FROM MovieItem")
    fun clearItem()

    @Query("SELECT * FROM MovieItem ORDER BY hot DESC")
    fun allItemLive(): LiveData<List<MovieItem>>

}