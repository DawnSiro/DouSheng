package com.qxy.dousheng.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendItem(
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "gender")
    val gender: Int,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "province")
    val province: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "is_follow")
    val isFollow: Int, // isFollow:1 noFollow:2 non:0
    @ColumnInfo(name = "flag")
    val flag: Int  // 标志位 fans:1 follow:2 non:0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}