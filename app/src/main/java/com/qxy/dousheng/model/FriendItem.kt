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
    val isFollow: Boolean,
    @ColumnInfo(name = "flag")
    val flag: Boolean  // 标志位 用于区分关注和粉丝
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}