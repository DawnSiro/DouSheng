package com.qxy.dousheng

import androidx.room.*

@Dao
interface ItemDao {
    @Insert
    fun insertItem(vararg item: Item);

    @Update
    fun updateItem(vararg item: Item);

    @Delete
    fun deleteItem(vararg item: Item);

    @Query("DELETE FROM Item")
    fun clearItem();

    @Query("SELECT * FROM Item ORDER BY hot DESC")
    fun allItem(): List<Item>

}